package com.example.jourie.data.repository

import com.example.jourie.data.model.EvolutionStage
import com.example.jourie.data.model.StreakData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.max

// Repository untuk data streak yang tersimpan di Firestore per user
class StreakRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Konfigurasi tahapan evolusi pet (aturan global, sama untuk semua user)
    private val baseTimeline: List<EvolutionStage> = listOf(
        EvolutionStage(level = 1, name = "Capybara", daysRequired = 1, isAchieved = false),
        EvolutionStage(level = 2, name = "Capybara", daysRequired = 3, isAchieved = false),
        EvolutionStage(level = 3, name = "Bear", daysRequired = 7, isAchieved = false),
        EvolutionStage(level = 4, name = "Dragon", daysRequired = 14, isAchieved = false)
    )

    /**
     * Mengambil data streak untuk user saat ini dari dokumen `users/{uid}`.
     * Jika field streak belum ada, nilai default akan digunakan.
     */
    suspend fun getStreakData(): StreakData {
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("User belum login, tidak bisa memuat streak")

        val userDoc = firestore.collection("users").document(uid).get().await()

        val currentDayStreak = (userDoc.getLong("currentDayStreak") ?: 0L).toInt()
        val currentPetLevel = (userDoc.getLong("currentPetLevel") ?: 1L).toInt()
        val highestEvolutionLevelAchieved = (userDoc.getLong("highestEvolutionLevelAchieved")
            ?: currentPetLevel.toLong()).toInt()

        // Hari yang sudah dijalani di level saat ini kita set sama dengan panjang streak sekarang
        val daysForCurrentLevel = currentDayStreak

        // Bangun timeline per user: tahap dengan level <= highestEvolutionLevelAchieved ditandai achieved
        val userTimeline = baseTimeline.map { stage ->
            stage.copy(isAchieved = stage.level <= highestEvolutionLevelAchieved)
        }

        val nextEvolution = userTimeline.getOrNull(currentPetLevel)
            ?: userTimeline.last()

        return StreakData(
            currentDayStreak = currentDayStreak,
            currentPetLevel = currentPetLevel,
            daysForCurrentLevel = daysForCurrentLevel,
            nextEvolution = nextEvolution,
            timeline = userTimeline
        )
    }

    /**
     * Dipanggil setiap kali user MENYELESAIKAN satu hari journaling.
     *
     * Aturan:
     * - Jika ini hari pertama: streak = 1.
     * - Jika tanggal baru = tanggal terakhir + 1 hari: streak bertambah 1.
     * - Jika lewat lebih dari 1 hari: streak di-reset ke 1.
     * - Kalau ada beberapa jurnal di hari yang sama: streak tidak bertambah lagi.
     */
    suspend fun updateStreakOnNewJournal(journalTimestamp: Long) {
        val uid = auth.currentUser?.uid ?: return

        val userRef = firestore.collection("users").document(uid)
        val userDoc = userRef.get().await()

        val currentStreak = (userDoc.getLong("currentDayStreak") ?: 0L).toInt()
        val currentPetLevel = (userDoc.getLong("currentPetLevel") ?: 1L).toInt()
        val totalJournalDays = (userDoc.getLong("totalJournalDays") ?: 0L).toInt()
        val highestEvolutionLevelAchieved = (userDoc.getLong("highestEvolutionLevelAchieved")
            ?: currentPetLevel.toLong()).toInt()
        val lastJournalDateStr = userDoc.getString("lastJournalDate")

        val newDateStr = formatDate(journalTimestamp) // format yyyy-MM-dd

        var newStreak = currentStreak
        var newTotalDays = totalJournalDays

        if (lastJournalDateStr == null) {
            // Pertama kali user journaling
            newStreak = 1
            newTotalDays = 1
        } else {
            val diffDays = daysBetween(lastJournalDateStr, newDateStr)
            when {
                diffDays <= 0L -> {
                    // Jurnal di hari yang sama atau mundur: tidak mengubah streak
                    return
                }
                diffDays == 1L -> {
                    // Hari berturut-turut
                    newStreak = currentStreak + 1
                    newTotalDays += 1
                }
                else -> {
                    // Terlewat 1 hari atau lebih -> reset ke 1
                    newStreak = 1
                    newTotalDays += 1
                }
            }
        }

        // Hitung level pet berdasarkan panjang streak dan konfigurasi timeline
        val newPetLevel = calculatePetLevel(newStreak)
        // Reset evolution level sesuai dengan current pet level (tidak pakai max lagi)
        val newHighestLevelAchieved = newPetLevel

        val data = mapOf(
            "currentDayStreak" to newStreak,
            "lastJournalDate" to newDateStr,
            "currentPetLevel" to newPetLevel,
            "totalJournalDays" to newTotalDays,
            "highestEvolutionLevelAchieved" to newHighestLevelAchieved
        )

        userRef.set(data, SetOptions.merge()).await()
    }

    private fun calculatePetLevel(streakDays: Int): Int {
        // Ambil level tertinggi yang syarat daysRequired-nya terpenuhi
        val achievedStage = baseTimeline
            .filter { stage -> streakDays >= stage.daysRequired }
            .maxByOrNull { stage -> stage.level }

        return achievedStage?.level ?: 1
    }

    private fun formatDate(timestampMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestampMillis
        // Normalisasi ke awal hari di zona waktu lokal
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    private fun daysBetween(startDateStr: String, endDateStr: String): Long {
        return try {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val startDate = formatter.parse(startDateStr)
            val endDate = formatter.parse(endDateStr)
            if (startDate == null || endDate == null) {
                0L
            } else {
                val diffMillis = endDate.time - startDate.time
                diffMillis / (24L * 60L * 60L * 1000L)
            }
        } catch (e: Exception) {
            0L
        }
    }
}
