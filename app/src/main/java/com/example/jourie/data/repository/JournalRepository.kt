package com.example.jourie.data.repository

import com.example.jourie.data.model.JournalEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Repository untuk mengambil riwayat jurnal dari Firestore
class JournalRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
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

            JournalEntry(
                id = id,
                dateLabel = if (day != 0 && month.isNotEmpty()) "$day $month" else "",
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
     */
    suspend fun deleteJournalEntry(journalId: String) {
        val uid = auth.currentUser?.uid ?: return

        firestore
            .collection("users")
            .document(uid)
            .collection("journals")
            .document(journalId)
            .delete()
            .await()
    }
}
