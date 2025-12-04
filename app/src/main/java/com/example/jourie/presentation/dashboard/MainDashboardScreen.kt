// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/dashboard/MainDashboardScreen.kt

package com.example.jourie.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jourie.navigation.Routes
import com.example.jourie.presentation.dashboard.components.*
import com.example.jourie.ui.theme.JourieTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashboardScreen(
    // DIPERBAIKI: Tambahkan NavController sebagai parameter agar bisa menerima dari NavGraph
    navController: NavController,
    viewModel: MainDashboardViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            AddJournalFab {
                // DIPERBAIKI: Aksi navigasi saat FAB diklik
                navController.navigate(Routes.ADD_JOURNAL)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            // Menggunakan LazyColumn untuk menata semua komponen secara vertikal dan efisien
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding), // Padding dari Scaffold agar tidak tertutup FAB
                contentPadding = PaddingValues(bottom = 24.dp), // Beri ruang di bagian bawah
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Item 1: Header
                item {
                    DashboardWelcomeHeader(username = state.username)
                }

                // Item 2: Konten di bawah Header dengan padding horizontal
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DailyStreakSection(streakCount = state.currentStreak)
                        DailyEmotionStats(emotions = state.todaysEmotions)
                        ReflectJournalCard(onWriteClick = {
                            // DIPERBAIKI: Aksi navigasi saat tombol "Write Journal" diklik
                            navController.navigate(Routes.ADD_JOURNAL)
                        })
                        DailyCalendarView()
                    }
                }

                // Item 3: (Recent Journals removed)

                // Item 4: Konten terakhir dengan padding horizontal
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(28.dp)
                    ) {
                        AchievementsProgressCard(progress = state.achievementsProgress)
                        WellnessRecommendations(recommendations = state.recommendations)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainDashboardScreenPreview() {
    // Untuk preview, kita bisa buat NavController palsu agar tidak error
    val navController = rememberNavController()
    JourieTheme {
        MainDashboardScreen(navController = navController)
    }
}
