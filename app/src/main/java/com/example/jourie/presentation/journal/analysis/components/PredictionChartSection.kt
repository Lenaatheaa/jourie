// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/journal/analysis/components/PredictionChartSection.kt

package com.example.jourie.presentation.journal.analysis.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.LightPurpleBg
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.TextDark

// Palet warna untuk chart
private val chartColors = listOf(
    Color(0xFFFFC107), // Kuning (Happy)
    Color(0xFF4A90E2), // Biru (Sad)
    Color(0xFFC27AFF), // Ungu (Calm)
    Color(0xFFD0021B), // Merah (Angry)
    Color(0xFF4CAF50), // Hijau (Peaceful)
    Color(0xFF9C27B0)  // Ungu Tua (Anxious)
)

@Composable
fun PredictionChartSection(emotionDistribution: Map<String, Float>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(LightPurpleBg)
            .padding(16.dp)
    ) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.AutoGraph,
                contentDescription = "Prediction",
                tint = PrimaryPurple
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Prediction",
                fontWeight = FontWeight.Bold,
                color = TextDark,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Body Text
        Text(
            text = "Based on your recent patterns, you are likely to experience continued emotional stability and mindfulness in the coming days.",
            color = TextDark.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Donut Chart dan Legend
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            DonutChart(
                emotions = emotionDistribution,
                colors = chartColors,
                modifier = Modifier.size(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        EmotionLegend(emotions = emotionDistribution, colors = chartColors)
    }
}

@Composable
private fun DonutChart(
    emotions: Map<String, Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    val total = emotions.values.sum()
    var startAngle = -90f

    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(1f, animationSpec = tween(durationMillis = 1500))
    }

    Canvas(modifier = modifier) {
        emotions.values.forEachIndexed { index, value ->
            val sweepAngle = (value / total) * 360f
            drawArc(
                color = colors[index % colors.size],
                startAngle = startAngle,
                sweepAngle = sweepAngle * animatedProgress.value,
                useCenter = false,
                style = Stroke(width = 35f, cap = StrokeCap.Butt)
            )
            startAngle += sweepAngle
        }
    }
}

@Composable
private fun EmotionLegend(emotions: Map<String, Float>, colors: List<Color>) {
    // Tampilkan 2 kolom legend
    val chunkedEmotions = emotions.toList().chunked(3) // Ubah ke 3 kolom agar lebih rapi
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        chunkedEmotions.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly // Ubah agar lebih merata
            ) {
                rowItems.forEach { (name, value) ->
                    LegendItem(
                        color = colors[emotions.keys.indexOf(name) % colors.size],
                        text = name,
                        percentage = (value * 100).toInt()
                    )
                }
            }
        }
    }
}

@Composable
private fun LegendItem(color: Color, text: String, percentage: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$text ($percentage%)", fontSize = 12.sp, color = TextDark.copy(alpha = 0.8f))
    }
}
