package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.jourie.ui.theme.Gray200
import com.example.jourie.ui.theme.Gray500
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.Purple100
import com.example.jourie.ui.theme.Purple700
import com.example.jourie.ui.theme.White

@Composable
fun DailyEmotionStats(emotions: List<EmotionSnapshot>) {
        // Empty state emotions with gray colors and 0%
        val displayEmotions = if (emotions.isEmpty()) {
                listOf(
                        EmotionSnapshot("Sadness", 0, "0%", Gray200),
                        EmotionSnapshot("Anger", 0, "0%", Gray200),
                        EmotionSnapshot("Happiness", 0, "0%", Gray200)
                )
        } else {
                emotions
        }

        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
                Column(modifier = Modifier.padding(20.dp)) {
                        // Header
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                                Text(
                                        text = "Today's Emotions",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Gray900
                                )
                                Box(
                                        modifier =
                                                Modifier.clip(RoundedCornerShape(12.dp))
                                                        .background(Purple100)
                                                        .padding(
                                                                horizontal = 12.dp,
                                                                vertical = 4.dp
                                                        )
                                ) {
                                        Text(
                                                text = "Live",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = Purple700
                                        )
                                }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                                // Donut Chart
                                EmotionDonutChart(emotions = displayEmotions, modifier = Modifier)

                                Spacer(modifier = Modifier.width(32.dp))

                                // Legend
                                Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                        displayEmotions.forEach { emotion ->
                                                Row(
                                                        verticalAlignment =
                                                                Alignment.CenterVertically,
                                                        horizontalArrangement =
                                                                Arrangement.spacedBy(6.dp)
                                                ) {
                                                        Box(
                                                                modifier =
                                                                        Modifier.size(10.dp)
                                                                                .clip(
                                                                                        androidx.compose
                                                                                                .foundation
                                                                                                .shape
                                                                                                .CircleShape
                                                                                )
                                                                                .background(
                                                                                        emotion.color
                                                                                )
                                                        )
                                                        Text(
                                                                text = emotion.name,
                                                                fontSize = 13.sp,
                                                                color = Gray900,
                                                                modifier = Modifier.width(80.dp)
                                                        )
                                                        Text(
                                                                text = "${emotion.percentage}%",
                                                                fontSize = 13.sp,
                                                                fontWeight = FontWeight.SemiBold,
                                                                color = Gray900
                                                        )
                                                }
                                        }
                                }
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // Horizontal bars
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                displayEmotions.forEach { emotion ->
                                        Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                        ) {
                                                Text(
                                                        text = emotion.name,
                                                        fontSize = 13.sp,
                                                        color = Gray500,
                                                        modifier = Modifier.width(80.dp),
                                                        maxLines = 1
                                                )

                                                Box(
                                                        modifier =
                                                                Modifier.weight(1f)
                                                                        .height(10.dp)
                                                                        .clip(
                                                                                RoundedCornerShape(
                                                                                        5.dp
                                                                                )
                                                                        )
                                                                        .background(Gray200)
                                                ) {
                                                        Box(
                                                                modifier =
                                                                        Modifier.fillMaxHeight()
                                                                                .fillMaxWidth(
                                                                                        emotion.percentage /
                                                                                                100f
                                                                                )
                                                                                .clip(
                                                                                        RoundedCornerShape(
                                                                                                5.dp
                                                                                        )
                                                                                )
                                                                                .background(
                                                                                        emotion.color
                                                                                )
                                                        )
                                                }

                                                Spacer(modifier = Modifier.width(12.dp))

                                                Text(
                                                        text = "${emotion.percentage}%",
                                                        fontSize = 13.sp,
                                                        fontWeight = FontWeight.SemiBold,
                                                        color = Gray900,
                                                        modifier = Modifier.width(35.dp)
                                                )
                                        }
                                }
                        }
                }
        }
}

@Preview(showBackground = true)
@Composable
private fun DailyEmotionStatsPreview() {
        val sampleEmotions =
                listOf(
                        EmotionSnapshot("Cemas", 90, "-3%", Color(0xFFF5A623)),
                        EmotionSnapshot("Sedih", 10, "-2%", Color(0xFF4A90E2)),
                        EmotionSnapshot("Tenang", 0, "+1%", Color(0xFF28A745)),
                        EmotionSnapshot("Senang", 0, "+1%", Color(0xFFD0021B))
                )
        JourieTheme { DailyEmotionStats(emotions = sampleEmotions) }
}
