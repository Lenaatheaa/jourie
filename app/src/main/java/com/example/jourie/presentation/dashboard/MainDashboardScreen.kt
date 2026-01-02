// File:
// A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/dashboard/MainDashboardScreen.kt

package com.example.jourie.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jourie.data.model.EmotionSnapshot
import com.example.jourie.data.model.WellnessRecommendation
import com.example.jourie.navigation.Routes
import com.example.jourie.presentation.dashboard.components.*
import com.example.jourie.ui.theme.Indigo50
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.Purple50
import com.example.jourie.ui.theme.White
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashboardScreen(
        // DIPERBAIKI: Tambahkan NavController sebagai parameter agar bisa menerima dari NavGraph
        navController: NavController,
        viewModel: MainDashboardViewModel = viewModel()
) {
        val state by viewModel.state.collectAsState()
        // Detect user change and refresh data
        val currentUserId = remember { FirebaseAuth.getInstance().currentUser?.uid }
        LaunchedEffect(currentUserId) {
                currentUserId?.let {
                        viewModel.refreshData()
                }
        }
        // Tempat untuk rekomendasi AI berbasis jurnal terbaru.
        // Jika belum ada analisis AI, akan tetap null dan UI memakai data dummy dari
        // state.recommendations.
        val aiRecommendationsState = remember {
                mutableStateOf<List<WellnessRecommendation>?>(null)
        }
        // Tempat untuk emosi harian berbasis skor AI pada jurnal terbaru.
        // Jika belum ada data AI, akan tetap null dan UI memakai data dummy dari
        // state.todaysEmotions.
        val aiEmotionsState = remember { mutableStateOf<List<EmotionSnapshot>?>(null) }

        LaunchedEffect(Unit) {
                try {
                        val auth = FirebaseAuth.getInstance()
                        val uid = auth.currentUser?.uid ?: return@LaunchedEffect
                        val firestore = FirebaseFirestore.getInstance()

                        // Ambil jurnal terbaru berdasarkan createdAt.
                        val latestJournalSnapshot =
                                firestore
                                        .collection("users")
                                        .document(uid)
                                        .collection("journals")
                                        .orderBy(
                                                "createdAt",
                                                com.google.firebase.firestore.Query.Direction
                                                        .DESCENDING
                                        )
                                        .limit(1)
                                        .get()
                                        .await()

                        val latestJournalId =
                                latestJournalSnapshot.documents.firstOrNull()?.id
                                        ?: return@LaunchedEffect

                        // Ambil analisis AI terbaru untuk jurnal tersebut.
                        val aiSnapshot =
                                firestore
                                        .collection("users")
                                        .document(uid)
                                        .collection("journals")
                                        .document(latestJournalId)
                                        .collection("aiAnalysis")
                                        .orderBy(
                                                "createdAt",
                                                com.google.firebase.firestore.Query.Direction
                                                        .DESCENDING
                                        )
                                        .limit(1)
                                        .get()
                                        .await()

                        val aiDoc = aiSnapshot.documents.firstOrNull() ?: return@LaunchedEffect

                        val recommendationText = aiDoc.getString("recommendation") ?: ""
                        val quoteText = aiDoc.getString("quote") ?: ""

                        // Jika field kosong (karena AI belum benar-benar diisi), gunakan teks
                        // default sebagai
                        // placeholder.
                        val wellnessTitle =
                                if (recommendationText.isNotBlank()) {
                                        recommendationText
                                } else {
                                        "Take a mindful 5-minute break today."
                                }

                        val meditationTitle =
                                if (quoteText.isNotBlank()) {
                                        quoteText
                                } else {
                                        "Try a short breathing meditation session."
                                }

                        aiRecommendationsState.value =
                                listOf(
                                        WellnessRecommendation(
                                                id = 1,
                                                category = "Recommendation",
                                                title = wellnessTitle
                                        ),
                                        WellnessRecommendation(
                                                id = 2,
                                                category = "Quote",
                                                title = meditationTitle
                                        )
                                )

                        // Ambil skor emosi untuk jurnal tersebut dari sub-koleksi emotionScores.
                        val scoresSnapshot =
                                firestore
                                        .collection("users")
                                        .document(uid)
                                        .collection("journals")
                                        .document(latestJournalId)
                                        .collection("emotionScores")
                                        .get()
                                        .await()

                        if (!scoresSnapshot.isEmpty) {
                                val aiEmotions =
                                        scoresSnapshot.documents.map { doc ->
                                                val name = doc.getString("emotionName") ?: "Emotion"
                                                val percentage =
                                                        (doc.getLong("score") ?: 0L).toInt()
                                                val trend =
                                                        (doc.getLong("comparisonTrend") ?: 0L)
                                                                .toInt()
                                                val change =
                                                        if (trend >= 0) "+${trend}%"
                                                        else "${trend}%"
                                                val colorHex =
                                                        doc.getString("colorHex") ?: "#4A90E2"
                                                val parsedColor =
                                                        try {
                                                                val safeHex =
                                                                        if (colorHex.startsWith("#")
                                                                        )
                                                                                colorHex
                                                                        else "#${colorHex}"
                                                                Color(
                                                                        android.graphics.Color
                                                                                .parseColor(safeHex)
                                                                )
                                                        } catch (_: IllegalArgumentException) {
                                                                Color(
                                                                        0xFF4A90E2
                                                                ) // Fallback blue color
                                                        }

                                                EmotionSnapshot(
                                                        name = name,
                                                        percentage = percentage,
                                                        change = change,
                                                        color = parsedColor
                                                )
                                        }

                                aiEmotionsState.value = aiEmotions
                        }
                } catch (_: Exception) {
                        // Jika gagal (belum ada jurnal/AI atau masalah jaringan), biarkan null dan
                        // gunakan data
                        // dummy lama.
                }
        }

        Scaffold { innerPadding ->
                if (state.isLoading) {
                        Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator() }
                } else {
                        // Menggunakan LazyColumn untuk menata semua komponen secara vertikal dan
                        // efisien
                        LazyColumn(
                                modifier =
                                        Modifier.fillMaxSize()
                                                .background(
                                                        androidx.compose.ui.graphics.Brush
                                                                .verticalGradient(
                                                                        colors =
                                                                                listOf(
                                                                                        Purple50,
                                                                                        White,
                                                                                        Indigo50
                                                                                )
                                                                )
                                                )
                                                .padding(
                                                        innerPadding
                                                ), // Padding dari Scaffold agar tidak tertutup FAB
                                contentPadding =
                                        PaddingValues(
                                                bottom = 120.dp
                                        ), // Beri ruang EXTRA agar tidak tertutup Navbar Overlay
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                                // Item 1: Header
                                item { DashboardWelcomeHeader(username = state.username) }

                                // Item 2: Konten di bawah Header dengan padding horizontal
                                item {
                                        Column(
                                                modifier = Modifier.padding(horizontal = 16.dp),
                                                verticalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                                DailyStreakSection(
                                                        streakCount = state.currentStreak
                                                )
                                                // Jika sudah ada data emosi dari AI, gunakan itu;
                                                // jika belum, fallback ke
                                                // data dummy dari state.
                                                val emotionsToShow =
                                                        aiEmotionsState.value
                                                                ?: state.todaysEmotions
                                                DailyEmotionStats(emotions = emotionsToShow)
                                                ReflectJournalCard(
                                                        onWriteClick = {
                                                                navController.navigate(
                                                                        Routes.ADD_JOURNAL
                                                                )
                                                        }
                                                )
                                                DailyCalendarView(
                                                        journalEntries = state.recentJournals,
                                                        onDateWithJournalClick = { dateQuery ->
                                                                navController.navigate(
                                                                        "${Routes.HISTORY}?dateFilter=$dateQuery"
                                                                )
                                                        }
                                                )
                                                AchievementsProgressCard(
                                                        progress = state.achievementsProgress
                                                )

                                                // Jika sudah ada rekomendasi dari AI, gunakan itu.
                                                val recsToShow =
                                                        aiRecommendationsState.value
                                                                ?: state.recommendations
                                                WellnessRecommendations(
                                                        recommendations = recsToShow
                                                )
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
        JourieTheme { MainDashboardScreen(navController = navController) }
}
