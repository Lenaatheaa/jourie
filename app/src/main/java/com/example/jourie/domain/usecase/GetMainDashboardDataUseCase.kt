// File: .../usecase/GetMainDashboardDataUseCase.kt
package com.example.jourie.domain.usecase

import com.example.jourie.data.model.Badge
import com.example.jourie.data.firebase.FirebaseAuthRepository
import com.example.jourie.data.repository.MainDashboardRepository
import com.example.jourie.data.repository.MilestonesRepository
import com.example.jourie.data.repository.StreakRepository
import com.example.jourie.data.repository.JournalRepository
import com.example.jourie.presentation.dashboard.MainDashboardState

class GetMainDashboardDataUseCase(
    private val dashboardRepository: MainDashboardRepository,
    private val streakRepository: StreakRepository = StreakRepository(),
    private val milestonesRepository: MilestonesRepository = MilestonesRepository(),
    private val journalRepository: JournalRepository = JournalRepository(),
    private val authRepository: FirebaseAuthRepository = FirebaseAuthRepository()
) {
    suspend operator fun invoke(): Result<MainDashboardState> {
        return runCatching {
            // Data lain (emosi, rekomendasi, dll.) masih menggunakan repository dashboard.
            val (emotions, _, recommendations) = dashboardRepository.getDashboardData()

            // Jurnal terbaru diambil langsung dari Firestore lewat JournalRepository.
            val journals = journalRepository.getAllJournalEntries()

            // Ambil data streak aktual dari Firestore (users/{uid}).
            val streakData = streakRepository.getStreakData()

            // Ambil semua badges dari Firestore dan hitung progres per kategori.
            val allBadges = milestonesRepository.getAllBadges()
            val streakBadges = allBadges.filter { it.category == "Streak" }
            val journalBadges = allBadges.filter { it.category == "Journal" }

            fun calcCategoryProgress(badges: List<Badge>): Int {
                if (badges.isEmpty()) return 0
                val unlocked = badges.count { it.isUnlocked }
                return ((unlocked.toFloat() / badges.size.toFloat()) * 100f).toInt()
            }

            val streakProgress = calcCategoryProgress(streakBadges)
            val journalProgress = calcCategoryProgress(journalBadges)

            val achievementsProgress = mapOf(
                "Streak" to streakProgress,
                // Belum ada achievements khusus Emotions, sementara di-set 0
                "Emotions" to 0,
                "Journals" to journalProgress
            )

            // Ambil nama lengkap user dari Firestore lalu ambil nama depan saja
            val profileResult = authRepository.getCurrentUserProfile()
            val fullName = profileResult.getOrNull()?.fullName?.trim().orEmpty()
            val firstName = fullName.split(" ").firstOrNull().orEmpty().ifBlank { "Friend" }

            MainDashboardState(
                username = firstName,
                currentStreak = streakData.currentDayStreak,
                emotionSummaries = emotions,
                todaysEmotions = emotions.take(3),
                recentJournals = journals,
                recommendations = recommendations,
                achievementsProgress = achievementsProgress,
                isLoading = false
            )
        }
    }
}
    