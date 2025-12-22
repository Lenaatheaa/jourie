package com.example.jourie.presentation.streak
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
//mport com.example.jourie.data.repository.StreakRepository
import com.example.jourie.data.repository.StreakRepository
import com.example.jourie.domain.usecase.CalculateEvolutionProgressUseCase
import com.example.jourie.domain.usecase.GetStreakDataUseCase
import com.example.jourie.presentation.streak.components.*
import com.example.jourie.ui.theme.IconGray
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.White

@Composable
fun StreakScreen(
    // Injeksi ViewModel jika menggunakan Hilt. Jika tidak, kita buat factory sederhana.
    viewModel: StreakViewModel = viewModel(
        factory = StreakViewModelFactory(
            GetStreakDataUseCase(StreakRepository()),
            CalculateEvolutionProgressUseCase()
        )
    )
) {
    val state by viewModel.state.collectAsState()


    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (state.streakData != null) {
        val data = state.streakData!!
        // KONTEN UTAMA LAYAR STREAK
        Column(
            modifier = Modifier
                .fillMaxSize()
                // Padding dari Scaffold sudah tidak ada, jadi kita tambahkan padding manual
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            StreakHeader(streakDays = data.currentDayStreak)
            PetDisplaySection(level = data.currentPetLevel)
            NextEvolutionSection(
                nextEvolutionName = data.nextEvolution.name,
                daysToEvolve = data.nextEvolution.daysRequired,
                progress = state.evolutionProgress,
                currentStreakDays = data.currentDayStreak
            )
            EvolutionTimelineSection(timeline = data.timeline)
            RecentStreakDaysSection()
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = state.error ?: "Gagal memuat data")
        }
    }
}

// Factory ViewModel tetap tidak berubah, ini sudah benar
class StreakViewModelFactory(
    private val getStreakDataUseCase: GetStreakDataUseCase,
    private val calculateEvolutionProgressUseCase: CalculateEvolutionProgressUseCase
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StreakViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StreakViewModel(getStreakDataUseCase, calculateEvolutionProgressUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Preview(showSystemUi = true)
@Composable
private fun StreakScreenPreview() {
    JourieTheme {
        StreakScreen()
    }
}