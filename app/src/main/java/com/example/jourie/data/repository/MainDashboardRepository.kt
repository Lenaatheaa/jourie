// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/data/repository/MainDashboardRepository.kt

package com.example.jourie.data.repository

import androidx.compose.ui.graphics.Color
import com.example.jourie.data.model.EmotionSnapshot
import com.example.jourie.data.model.JournalEntry // Menggunakan kelas yang sudah ada
import com.example.jourie.data.model.WellnessRecommendation
import kotlinx.coroutines.delay

/**
 * Kelas ini bertanggung jawab untuk menyediakan data yang dibutuhkan oleh Dashboard.
 * Di aplikasi nyata, data ini akan berasal dari database lokal (seperti Room) atau API.
 */
class MainDashboardRepository {

    /**
     * Fungsi ini mengambil semua data yang diperlukan untuk Dashboard.
     * Menggunakan 'suspend' karena di aplikasi nyata ini akan menjadi operasi I/O.
     */
    suspend fun getDashboardData(): Triple<List<EmotionSnapshot>, List<JournalEntry>, List<WellnessRecommendation>> {
        delay(500) // Simulasi waktu tunggu saat mengambil data

        // Return empty list - actual data comes from AI analysis on latest journal
        val emotions = emptyList<EmotionSnapshot>()

        // Data dummy untuk jurnal terbaru (menggunakan 'JournalEntry' yang sudah ada)
        val journals = listOf(
            JournalEntry("1", "Today", 24, "Nov", "Calm", "Had a wonderful morning meditation session. Felt incredibly calm and centered.", listOf("grateful", "peaceful")),
            JournalEntry("2", "Yesterday", 23, "Nov", "Stressed", "Work was challenging today. Felt overwhelmed by deadlines but managed to push through.", listOf("overwhelmed", "challenging"))
        )

        // Data dummy untuk rekomendasi
        val recommendations = listOf(
            WellnessRecommendation(1, "Recommendation", "No recommendation yet. Start journaling to get personalized insights!"),
            WellnessRecommendation(2, "Quote", "No quote available yet. Keep writing to receive inspiring quotes!")
        )

        // Mengembalikan semua data sekaligus menggunakan 'Triple'
        return Triple(emotions, journals, recommendations)
    }
}
