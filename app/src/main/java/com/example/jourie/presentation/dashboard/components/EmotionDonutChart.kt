package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.data.model.EmotionSnapshot
import com.example.jourie.ui.theme.Black
import com.example.jourie.ui.theme.Gray500

@Composable
fun EmotionDonutChart(emotions: List<EmotionSnapshot>, modifier: Modifier = Modifier) {
    val totalPercentage = emotions.sumOf { it.percentage }
    val isEmpty = totalPercentage == 0

    Box(modifier = modifier.size(150.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 32.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            if (isEmpty) {
                // Draw full gray circle when empty
                drawArc(
                        color = com.example.jourie.ui.theme.Gray200,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
            } else {
                // Draw emotion arcs
                var startAngle = -90f

                emotions.forEach { emotion ->
                    val sweepAngle = (emotion.percentage / 100f) * 360f

                    drawArc(
                            color = emotion.color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            topLeft = Offset(center.x - radius, center.y - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                    )

                    startAngle += sweepAngle
                }
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                    text = if (isEmpty) "0%" else "100%",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    textAlign = TextAlign.Center
            )
            Text(text = "Total", fontSize = 13.sp, color = Gray500, textAlign = TextAlign.Center)
        }
    }
}
