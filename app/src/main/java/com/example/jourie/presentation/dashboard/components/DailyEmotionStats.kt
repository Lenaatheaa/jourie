// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/dashboard/components/DailyEmotionStats.kt

package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.data.model.EmotionSnapshot
import com.example.jourie.ui.theme.BorderGray
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.TextDark
import com.example.jourie.ui.theme.White

@Composable
fun DailyEmotionStats(emotions: List<EmotionSnapshot>) {
    Column {
        Text(
            text = "Today's Emotions",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            emotions.forEach { emotion ->
                EmotionStatCard(
                    emotion = emotion,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun EmotionStatCard(emotion: EmotionSnapshot, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(White)
            .border(1.dp, BorderGray, RoundedCornerShape(18.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = emotion.name,
            fontSize = 14.sp,
            color = TextDark.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${emotion.percentage}%",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = emotion.color
        )
        Text(
            text = emotion.change,
            fontSize = 12.sp,
            color = if ((emotion.change.firstOrNull() ?: '+') == '+') Color(0xFF28A745) else Color(0xFFFF5757)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DailyEmotionStatsPreview() {
    val sampleEmotions = listOf(
        EmotionSnapshot("Sadness", 10, "-3%", Color(0xFF4A90E2)),
        EmotionSnapshot("Anger", 15, "-2%", Color(0xFFD0021B)),
        EmotionSnapshot("Happiness", 12, "+1%", Color(0xFFF5A623))
    )
    JourieTheme {
        DailyEmotionStats(emotions = sampleEmotions)
    }
}
