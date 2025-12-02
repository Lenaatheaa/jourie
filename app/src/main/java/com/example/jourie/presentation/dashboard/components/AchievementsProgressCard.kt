package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.*

@Composable
fun AchievementsProgressCard(progress: Map<String, Int>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Achievements",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            ProgressItem(label = "Yesterday's Emotion Summary", progress = 0.2f, color = PrimaryBlue) // Contoh
            ProgressItem(label = "Streak", progress = (progress["Streak"] ?: 0) / 100f, color = PrimaryBlue)
            ProgressItem(label = "Emotions", progress = (progress["Emotions"] ?: 0) / 100f, color = PrimaryBlue)
            ProgressItem(label = "Journals", progress = (progress["Journals"] ?: 0) / 100f, color = PrimaryBlue)
        }
    }
}

@Composable
private fun ProgressItem(label: String, progress: Float, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            color = TextDark,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${(progress * 100).toInt()}%",
            color = PrimaryPurple,
            fontWeight = FontWeight.SemiBold
        )
    }
    Spacer(modifier = Modifier.height(0.dp))
    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(4.dp)),
        color = color,
        trackColor = Color(0xFFE8E8E8)
    )
}
