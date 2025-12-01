// File: .../usecase/GetMainDashboardDataUseCase.kt
package com.example.jourie.domain.usecase

import com.example.jourie.data.repository.MainDashboardRepository
import com.example.jourie.presentation.dashboard.MainDashboardState

class GetMainDashboardDataUseCase(
    private val repository: MainDashboardRepository
) {
    suspend operator fun invoke(): Result<MainDashboardState> {
        return runCatching {
            val (emotions, journals, recommendations) = repository.getDashboardData()
            MainDashboardState(
                username = "Alex",
                currentStreak = 15,
                emotionSummaries = emotions,
                todaysEmotions = emotions.take(3),
                recentJournals = journals,
                recommendations = recommendations,
                achievementsProgress = mapOf(
                    "Streak" to 30,
                    "Emotions" to 60,
                    "Journals" to 10
                ),
                isLoading = false
            )
        }
    }
}
    