// File: .../presentation/achievements/MilestonesScreen.kt

package com.example.jourie.presentation.achievements

// biasa
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn // DIGANTI: Menggunakan LazyColumn, bukan Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourie.presentation.achievements.components.*
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MilestonesScreen(viewModel: MilestonesViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    // Detect user change and refresh data
    val currentUserId = remember { FirebaseAuth.getInstance().currentUser?.uid }
    LaunchedEffect(currentUserId) {
        currentUserId?.let {
            viewModel.refreshData()
        }
    }

    // Hapus 'Scaffold' dan 'JourieTheme' jika sudah ditangani di MainActivity
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        // DIPERBAIKI: Menggunakan LazyColumn untuk menata semua komponen.
        // Ini adalah cara yang benar untuk performa dan layout yang kompleks.
        LazyColumn(
                modifier = Modifier.fillMaxSize().background(White),
                contentPadding =
                        PaddingValues(
                                bottom = 120.dp
                        ), // Padding diatur di sini agar tidak tertutup navbar
                verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Item 1: Header
            item {
                MilestonesHeader(
                        userName = state.userName,
                        badgeCount = state.badgeCount,
                        progress = state.progressPercent
                )
            }

            // Item 2: Judul Overview
            item {
                Text(
                        text = "Achievements Overview",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gray900,
                        modifier = Modifier.padding(start = 16.dp)
                )
            }

            // Item 3: Grid Kategori Streak
            item { MilestoneCategoryGrid(title = "Streak", badges = state.streakBadges) }

            // Item 4: Grid Kategori Jurnal
            item { MilestoneCategoryGrid(title = "Journals", badges = state.journalBadges) }

            // Item 5: Kartu CTA
            item { CtaUnlockCard() }

            // Item 6: Tombol Share
            item { ShareMilestonesButton(onClick = {}) }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MilestonesScreenPreview() {
    JourieTheme { MilestonesScreen() }
}
