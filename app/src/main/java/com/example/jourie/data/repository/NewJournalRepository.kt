package com.example.jourie.data.repository

import com.example.jourie.data.model.NewJournal
import com.example.jourie.data.repository.StreakRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// Repository untuk menyimpan jurnal baru ke Firestore
class NewJournalRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val streakRepository: StreakRepository = StreakRepository(auth, firestore)
) {

    /**
     * Menyimpan entri jurnal mentah ke Firestore pada path:
     * users/{uid}/journals/{journalId}
     *
     * Field yang disimpan:
     * - content: teks journaling
     * - createdAt: FieldValue.serverTimestamp()
     * - day: tanggal (1..31)
     * - month: singkatan bulan ("Jan", "Feb", dst.)
     * - mood: mood dari model NewJournal
     * - dateTimestamp: timestamp lokal sebagai backup
     *
     * Mengembalikan ID dokumen journal yang baru dibuat.
     */
    suspend fun insertJournal(journal: NewJournal): String {
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("User belum login, tidak bisa menyimpan jurnal")

        val calendar = Calendar.getInstance().apply {
            timeInMillis = journal.dateTimestamp
        }
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
        val month = monthFormat.format(Date(journal.dateTimestamp))

        val data = mapOf(
            "content" to journal.content,
            "createdAt" to FieldValue.serverTimestamp(),
            "day" to day,
            "month" to month,
            "mood" to journal.mood,
            "dateTimestamp" to journal.dateTimestamp
        )

        val docRef = firestore
            .collection("users")
            .document(uid)
            .collection("journals")
            .add(data)
            .await()

        // Update streak harian berdasarkan tanggal jurnal ini
        streakRepository.updateStreakOnNewJournal(journal.dateTimestamp)

        return docRef.id
    }

    // ==================== PLACEHOLDER UNTUK ANALISIS AI ====================

    data class AiAnalysisData(
        val dominantEmotion: String,
        val predictionSummary: String,
        val quote: String,
        val recommendation: String,
        val rootCause: String,
        val createdAt: Long = System.currentTimeMillis()
    )

    data class EmotionScoreData(
        val emotionName: String,
        val score: Int,
        val colorHex: String,
        val comparisonTrend: Int
    )

    /**
     * Menyimpan hasil analisis AI ke sub-koleksi:
     * users/{uid}/journals/{journalId}/aiAnalysis/{autoId}
     */
    suspend fun saveAiAnalysis(
        journalId: String,
        analysis: AiAnalysisData
    ) {
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("User belum login, tidak bisa menyimpan analisis AI")

        val data = mapOf(
            "dominantEmotion" to analysis.dominantEmotion,
            "predictionSummary" to analysis.predictionSummary,
            "quote" to analysis.quote,
            "recommendation" to analysis.recommendation,
            "rootCause" to analysis.rootCause,
            "createdAt" to FieldValue.serverTimestamp()
        )

        firestore
            .collection("users")
            .document(uid)
            .collection("journals")
            .document(journalId)
            .collection("aiAnalysis")
            .add(data)
            .await()
    }

    /**
     * Menyimpan daftar skor emosi ke sub-koleksi:
     * users/{uid}/journals/{journalId}/emotionScores/{autoId}
     */
    suspend fun saveEmotionScores(
        journalId: String,
        scores: List<EmotionScoreData>
    ) {
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("User belum login, tidak bisa menyimpan skor emosi")

        val collectionRef = firestore
            .collection("users")
            .document(uid)
            .collection("journals")
            .document(journalId)
            .collection("emotionScores")

        for (score in scores) {
            val data = mapOf(
                "emotionName" to score.emotionName,
                "score" to score.score,
                "colorHex" to score.colorHex,
                "comparisonTrend" to score.comparisonTrend
            )
            collectionRef.add(data).await()
        }
    }

    // ==================== PLACEHOLDER PEMBACAAN ANALISIS AI ====================

    /**
     * Mengambil satu dokumen analisis AI terbaru untuk jurnal tertentu.
     * Path: users/{uid}/journals/{journalId}/aiAnalysis
     */
    suspend fun getLatestAiAnalysis(journalId: String): AiAnalysisData? {
        val uid = auth.currentUser?.uid ?: return null

        val snapshot = firestore
            .collection("users")
            .document(uid)
            .collection("journals")
            .document(journalId)
            .collection("aiAnalysis")
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()

        val doc = snapshot.documents.firstOrNull() ?: return null
        return AiAnalysisData(
            dominantEmotion = doc.getString("dominantEmotion") ?: "",
            predictionSummary = doc.getString("predictionSummary") ?: "",
            quote = doc.getString("quote") ?: "",
            recommendation = doc.getString("recommendation") ?: "",
            rootCause = doc.getString("rootCause") ?: "",
            createdAt = (doc.getTimestamp("createdAt")?.toDate()?.time) ?: System.currentTimeMillis()
        )
    }

    /**
     * Mengambil semua skor emosi untuk jurnal tertentu.
     * Path: users/{uid}/journals/{journalId}/emotionScores
     */
    suspend fun getEmotionScores(journalId: String): List<EmotionScoreData> {
        val uid = auth.currentUser?.uid ?: return emptyList()

        val snapshot = firestore
            .collection("users")
            .document(uid)
            .collection("journals")
            .document(journalId)
            .collection("emotionScores")
            .get()
            .await()

        return snapshot.documents.map { doc ->
            EmotionScoreData(
                emotionName = doc.getString("emotionName") ?: "",
                score = (doc.getLong("score") ?: 0L).toInt(),
                colorHex = doc.getString("colorHex") ?: "",
                comparisonTrend = (doc.getLong("comparisonTrend") ?: 0L).toInt()
            )
        }
    }
}
