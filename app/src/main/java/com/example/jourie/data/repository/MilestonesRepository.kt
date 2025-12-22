package com.example.jourie.data.repository

import com.example.jourie.data.model.Badge
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repository untuk menghitung daftar achievements/badges user
 * berbasis data nyata di Firestore (streak & journaling),
 * tanpa menyimpan status badge secara terpisah per user.
 */
class MilestonesRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    /**
     * Mengambil seluruh badge (Streak & Journals) untuk user saat ini.
     * Field yang dipakai dari dokumen `users/{uid}`:
     * - currentDayStreak
     * - totalJournalDays
     */
    suspend fun getAllBadges(): List<Badge> {
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("User belum login, tidak bisa memuat achievements")

        val userRef = firestore.collection("users").document(uid)
        val userDoc = userRef.get().await()

        val currentDayStreak = (userDoc.getLong("currentDayStreak") ?: 0L).toInt()

        // Hitung jumlah total jurnal dari subkoleksi `journals`
        val journalsSnapshot = userRef.collection("journals").get().await()
        val totalJournalEntries = journalsSnapshot.size()

        val streakBadges = buildStreakBadges(currentDayStreak)
        val journalBadges = buildJournalBadges(totalJournalEntries)

        return streakBadges + journalBadges
    }

    // Konfigurasi global untuk badge kategori Streak
    private fun buildStreakBadges(currentStreak: Int): List<Badge> {
        data class StreakRule(
            val id: Int,
            val level: Int,
            val requiredDays: Int,
            val icon: String
        )

        val rules = listOf(
            StreakRule(1, 1, 3, "🔥"),
            StreakRule(2, 2, 7, "✨"),
            StreakRule(3, 3, 14, "🏅"),
            StreakRule(4, 4, 21, "💫"),
            StreakRule(5, 5, 30, "🏆"),
            StreakRule(6, 6, 60, "👑")
        )

        return rules.map { rule ->
            val unlocked = currentStreak >= rule.requiredDays
            Badge(
                id = rule.id,
                title = "Level ${rule.level}",
                subtitle = "${rule.requiredDays} days in a row",
                category = "Streak",
                isUnlocked = unlocked,
                icon = rule.icon
            )
        }
    }

    // Konfigurasi global untuk badge kategori Journals (berdasarkan jumlah jurnal)
    private fun buildJournalBadges(totalJournalEntries: Int): List<Badge> {
        data class JournalRule(
            val id: Int,
            val level: Int,
            val requiredCount: Int,
            val icon: String
        )

        val rules = listOf(
            JournalRule(101, 1, 1, "📝"),   // Level 1: 1 journal
            JournalRule(102, 2, 20, "📘"),  // Level 2: 20 journals
            JournalRule(103, 3, 50, "📗"),  // Level 3: 50 journals
            JournalRule(104, 4, 100, "📙"), // Level 4: 100 journals
            JournalRule(105, 5, 200, "📔")  // Level 5: 200 journals
        )

        return rules.map { rule ->
            val unlocked = totalJournalEntries >= rule.requiredCount
            val countLabel = if (rule.requiredCount == 1) "1 journal" else "${rule.requiredCount} journals"
            Badge(
                id = rule.id,
                title = "Level ${rule.level}",
                subtitle = countLabel,
                category = "Journal",
                isUnlocked = unlocked,
                icon = rule.icon
            )
        }
    }
}
