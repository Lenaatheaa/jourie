// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/achievements/components/MilestoneCategoryGrid.kt

package com.example.jourie.presentation.achievements.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.data.model.Badge
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.TextDark

@Composable
fun MilestoneCategoryGrid(
    title: String,
    badges: List<Badge>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            // DIPERBAIKI: Memberikan tinggi yang tetap pada LazyVerticalGrid
            // Ini akan memastikan grid dirender dengan benar di dalam Column yang scrollable.
            // Nilai 260.dp cukup untuk menampilkan 2 baris item.
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            // PENTING: Nonaktifkan scroll internal grid agar tidak konflik dengan scroll utama
            userScrollEnabled = false,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(badges, key = { it.id }) { badge ->
                MilestoneItemCard(badge = badge)
            }
        }
    }
}

// Preview ditambahkan untuk memudahkan pengecekan
@Preview(showBackground = true)
@Composable
private fun MilestoneCategoryGridPreview() {
    val sampleBadges = listOf(
        Badge(1, "Streak 1", "3 days", "Streak", true, "🔥"),
        Badge(2, "Streak 2", "7 days", "Streak", true, "✨"),
        Badge(3, "Streak 3", "14 days", "Streak", false, "🔒"),
        Badge(4, "Streak 4", "21 days", "Streak", true, "💫"),
        Badge(5, "Streak 5", "30 days", "Streak", false, "🏆"),
        Badge(6, "Streak 6", "60 days", "Streak", false, "👑")
    )
    JourieTheme {
        MilestoneCategoryGrid(title = "Streak", badges = sampleBadges)
    }
}
