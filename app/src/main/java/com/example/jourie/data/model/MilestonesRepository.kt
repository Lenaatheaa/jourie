package com.example.jourie.data.repository

import com.example.jourie.data.model.Badge
import kotlinx.coroutines.delay

// Repository dengan nama unik untuk data dummy pencapaian
class MilestonesRepository {
    suspend fun getAllBadges(): List<Badge> {
        delay(500) // Simulasi loading
        return listOf(
            // Streak Category
            Badge(1, "Streak 1", "3 days", "Streak", true, "🔥"),
            Badge(2, "Streak 2", "7 days", "Streak", true, "✨"),
            Badge(3, "Streak 3", "14 days", "Streak", false, "🔒"),
            Badge(4, "Streak 4", "21 days", "Streak", true, "💫"),
            Badge(5, "Streak 5", "30 days", "Streak", false, "🏆"),
            Badge(6, "Streak 6", "60 days", "Streak", false, "👑"),
            // Journal Category
            Badge(7, "Journal 1", "5 entries", "Journal", true, "📝"),
            Badge(8, "Journal 2", "10 entries", "Journal", true, "📚"),
            Badge(9, "Journal 3", "25 entries", "Journal", false, "🖋️"),
            Badge(10, "Journal 4", "50 entries", "Journal", true, "📖"),
            Badge(11, "Journal 5", "100 entries", "Journal", false, "📜"),
            Badge(12, "Journal 6", "200 entries", "Journal", false, "✍️")
        )
    }
}
