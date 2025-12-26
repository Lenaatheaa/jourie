package com.example.jourie.data.repository

import com.example.jourie.data.model.JournalEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Repository untuk mengambil riwayat jurnal dari Firestore
class JournalRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    /**
     * Menghitung label tanggal relatif berdasarkan selisih hari dari sekarang
     */
    private fun calculateRelativeDate(timestampMillis: Long): String {
        val journalDate = Calendar.getInstance().apply { timeInMillis = timestampMillis }
        val today = Calendar.getInstance()
        
        // Reset jam ke 00:00:00 untuk perbandingan hari saja
        journalDate.set(Calendar.HOUR_OF_DAY, 0)
        journalDate.set(Calendar.MINUTE, 0)
        journalDate.set(Calendar.SECOND, 0)
        journalDate.set(Calendar.MILLISECOND, 0)
        
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        
        val diffMillis = today.timeInMillis - journalDate.timeInMillis
        val daysDiff = (diffMillis / (1000 * 60 * 60 * 24)).toInt()
        
        return when {
            daysDiff == 0 -> "Today"
            daysDiff == 1 -> "Yesterday"
            daysDiff == 2 -> "2 days ago"
            daysDiff == 3 -> "3 days ago"
            daysDiff == 4 -> "4 days ago"
            daysDiff == 5 -> "5 days ago"
            daysDiff == 6 -> "6 days ago"
            daysDiff in 7..13 -> "1 week ago"
            daysDiff in 14..20 -> "2 weeks ago"
            daysDiff in 21..27 -> "3 weeks ago"
            daysDiff in 28..34 -> "4 weeks ago"
            else -> {
                // Lebih dari 34 hari, gunakan format normal
                val day = journalDate.get(Calendar.DAY_OF_MONTH)
                val month = SimpleDateFormat("MMM", Locale.getDefault()).format(journalDate.time)
                "$day $month"
            }
        }
    }

    /**
     * Mengambil semua jurnal milik user saat ini dari path:
     * users/{uid}/journals
     *
     * Mapping field (disesuaikan dengan struktur koleksi yang sudah ada):
     * - id            <- document.id
     * - description   <- content
     * - mood          <- mood
     * - dayOfMonth    <- day
     * - monthAbbreviation <- month
     * - tags          <- tags (array of string), default empty list
     * - dateLabel     <- "$day $month" (sederhana, bisa disempurnakan nanti)
     */
    suspend fun getAllJournalEntries(): List<JournalEntry> {
        val uid = auth.currentUser?.uid ?: return emptyList()

        val snapshot = firestore
            .collection("users")
            .document(uid)
            .collection("journals")
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .await()

        return snapshot.documents.map { doc ->
            val id = doc.id
            val day = (doc.getLong("day") ?: 0L).toInt()
            val month = doc.getString("month") ?: ""
            val mood = doc.getString("mood") ?: "Neutral"
            val content = doc.getString("content") ?: ""
            val tags = (doc.get("tags") as? List<*>)
                ?.filterIsInstance<String>()
                ?: emptyList()
            
            // Ambil timestamp untuk calculate relative date
            val timestamp = doc.getLong("dateTimestamp") ?: System.currentTimeMillis()
            val dateLabel = calculateRelativeDate(timestamp)

            JournalEntry(
                id = id,
                dateLabel = dateLabel,
                dayOfMonth = day,
                monthAbbreviation = month,
                mood = mood,
                description = content,
                tags = tags
            )
        }
    }

    /**
     * Menghapus satu jurnal milik user saat ini berdasarkan document id:
     * users/{uid}/journals/{journalId}
     * 
     * Cascade delete: Menghapus parent document beserta semua subcollections
     * untuk menghindari ghost documents di Firestore
     */
    suspend fun deleteJournalEntry(journalId: String) {
        val uid = auth.currentUser?.uid ?: return

        val journalRef = firestore
            .collection("users")
            .document(uid)
            .collection("journals")
            .document(journalId)

        try {
            // 1. Hapus subcollection aiAnalysis
            val aiAnalysisDocs = journalRef
                .collection("aiAnalysis")
                .get()
                .await()
            
            aiAnalysisDocs.documents.forEach { doc ->
                doc.reference.delete().await()
            }

            // 2. Hapus subcollection emotionScores
            val emotionScoresDocs = journalRef
                .collection("emotionScores")
                .get()
                .await()
            
            emotionScoresDocs.documents.forEach { doc ->
                doc.reference.delete().await()
            }

            // 3. Hapus parent document
            journalRef.delete().await()
            
        } catch (e: Exception) {
            // Jika ada error, tetap coba hapus parent document
            journalRef.delete().await()
        }
    }
}
