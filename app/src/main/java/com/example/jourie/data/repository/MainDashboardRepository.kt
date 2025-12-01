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

        // Data dummy untuk ringkasan emosi
        val emotions = listOf(
            EmotionSnapshot("Sadness", 10, "-3%", Color(0xFF4A90E2)),
            EmotionSnapshot("Anger", 15, "-2%", Color(0xFFD0021B)),
            EmotionSnapshot("Happiness", 12, "+1%", Color(0xFFF5A623)),
            EmotionSnapshot("Fear", 5, "+1%", Color(0xFFBD10E0)),
            EmotionSnapshot("Surprise", 8, "0%", Color(0xFF50E3C2))
        )

        // Data dummy untuk jurnal terbaru (menggunakan 'JournalEntry' yang sudah ada)
        val journals = listOf(
            JournalEntry(1, "Today", 24, "Nov", "Calm", "Had a wonderful morning meditation session. Felt incredibly calm and centered.", listOf("grateful", "peaceful")),
            JournalEntry(2, "Yesterday", 23, "Nov", "Stressed", "Work was challenging today. Felt overwhelmed by deadlines but managed to push through.", listOf("overwhelmed", "challenging"))
        )

        // Data dummy untuk rekomendasi
        val recommendations = listOf(
            WellnessRecommendation(1, "Wellness", "Meditation guide"),
            WellnessRecommendation(2, "Meditation for Beginners", "Calm your mind")
        )

        // Mengembalikan semua data sekaligus menggunakan 'Triple'
        return Triple(emotions, journals, recommendations)
    }
}
