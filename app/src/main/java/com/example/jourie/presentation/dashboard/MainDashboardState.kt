package com.example.jourie.presentation.dashboard

import com.example.jourie.data.model.EmotionSnapshot
import com.example.jourie.data.model.JournalEntry // DIGABUNGKAN
import com.example.jourie.data.model.WellnessRecommendation

data class MainDashboardState(
    val username: String = "Alex",
    val currentStreak: Int = 0,
    val emotionSummaries: List<EmotionSnapshot> = emptyList(), // Untuk Pager
    val todaysEmotions: List<EmotionSnapshot> = emptyList(), // Untuk 3 card
    val recentJournals: List<JournalEntry> = emptyList(), // Tipe data sudah benar
    val recommendations: List<WellnessRecommendation> = emptyList(),
    val achievementsProgress: Map<String, Int> = emptyMap(),
    val isLoading: Boolean = true,
    val error: String? = null
)
