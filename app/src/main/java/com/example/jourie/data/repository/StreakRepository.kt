// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/data/repository/StreakRepository.ktpackage com.example.jourie.data.repository

// DIPERBAIKI: Dua import ini sekarang berada di baris terpisah
import com.example.jourie.data.model.EvolutionStage
import com.example.jourie.data.model.StreakData
import kotlinx.coroutines.delay

// Ini adalah FAKE repository. Di aplikasi nyata, data diambil dari database atau API.
class StreakRepository {
    suspend fun getStreakData(): StreakData {
        delay(500) // Simulasi loading data

        val timeline = listOf(
            EvolutionStage(level = 1, name = "Capybara", daysRequired = 3, isAchieved = true),
            EvolutionStage(level = 2, name = "Capybara", daysRequired = 7, isAchieved = false),
            EvolutionStage(level = 3, name = "Bear", daysRequired = 14, isAchieved = false),
            EvolutionStage(level = 4, name = "Dragon", daysRequired = 30, isAchieved = false)
        )

        return StreakData(
            currentDayStreak = 15,
            currentPetLevel = 1,
            daysForCurrentLevel = 3,
            nextEvolution = timeline[1], // Evolusi berikutnya adalah level 2
            timeline = timeline
        )
    }
}
