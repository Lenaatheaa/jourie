package com.example.jourie.presentation.journal.analysis.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Blue400
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.Purple200
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.Purple50
import com.example.jourie.ui.theme.Red500

// Color palette for emotions
private val emotionColors =
        mapOf(
                "Cemas" to Purple400,
                "Sedih" to Color(0xFFF87171),
                "Tenang" to Blue400,
                "Senang" to Color(0xFFFBBF24),
                "Anxious" to Purple400,
                "Sad" to Color(0xFFF87171),
                "Calm" to Blue400,
                "Happy" to Color(0xFFFBBF24),
                "Anger" to Red500,
                "Marah" to Red500
        )

@Composable
fun PredictionChartSection(emotionDistribution: Map<String, Float>, predictionText: String) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors =
                    CardDefaults.cardColors(
                            containerColor = Purple50
                    ), // Very light purple background
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // No shadow
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header with icon
            Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                        modifier = Modifier.size(40.dp).background(Purple200, CircleShape),
                        contentAlignment = Alignment.Center
                ) {
                    Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = "Prediction",
                            tint = Purple400,
                            modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                        text = "Prediction",
                        fontWeight = FontWeight.Bold,
                        color = Gray900,
                        fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Prediction text
            Text(
                    text = predictionText,
                    color = Gray900.copy(alpha = 0.8f),
                    fontSize = 15.sp,
                    lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Donut Chart
            Box(
                    modifier = Modifier.fillMaxWidth().height(220.dp),
                    contentAlignment = Alignment.Center
            ) {
                EmotionDonutChart(
                        emotionDistribution = emotionDistribution,
                        modifier = Modifier.size(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Legend
            EmotionLegend(emotionDistribution = emotionDistribution)
        }
    }
}

@Composable
private fun EmotionDonutChart(
        emotionDistribution: Map<String, Float>,
        modifier: Modifier = Modifier
) {
    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(1f, animationSpec = tween(durationMillis = 1200))
    }

    val total = emotionDistribution.values.sum()
    val dominantEmotion = emotionDistribution.maxByOrNull { it.value }
    val dominantPercentage = ((dominantEmotion?.value ?: 0f) * 100).toInt()

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // Donut chart
        Canvas(modifier = Modifier.fillMaxSize()) {
            var startAngle = -90f
            emotionDistribution.forEach { (emotion, value) ->
                val sweepAngle = (value / total) * 360f
                val color = emotionColors[emotion] ?: Color.Gray

                drawArc(
                        color = color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle * animatedProgress.value,
                        useCenter = false,
                        style = Stroke(width = 40f, cap = StrokeCap.Butt),
                        size = size,
                        topLeft = Offset.Zero
                )
                startAngle += sweepAngle
            }
        }

        // Center text
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            Text(
                    text = "$dominantPercentage%",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray900
            )
            Text(
                    text = dominantEmotion?.key ?: "",
                    fontSize = 14.sp,
                    color = Gray900.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun EmotionLegend(emotionDistribution: Map<String, Float>) {
    // Split into 2 columns
    val emotions = emotionDistribution.toList()
    val half = (emotions.size + 1) / 2

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        // Left column
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            emotions.take(half).forEach { (emotion, value) ->
                LegendItem(
                        color = emotionColors[emotion] ?: Color.Gray,
                        text = emotion,
                        percentage = (value * 100).toInt()
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Right column
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            emotions.drop(half).forEach { (emotion, value) ->
                LegendItem(
                        color = emotionColors[emotion] ?: Color.Gray,
                        text = emotion,
                        percentage = (value * 100).toInt()
                )
            }
        }
    }
}

@Composable
private fun LegendItem(color: Color, text: String, percentage: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(12.dp).background(color, CircleShape))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$text ($percentage%)", fontSize = 13.sp, color = Gray900.copy(alpha = 0.8f))
    }
}
