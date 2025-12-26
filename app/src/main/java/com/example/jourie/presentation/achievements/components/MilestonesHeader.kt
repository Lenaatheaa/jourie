package com.example.jourie.presentation.achievements.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MilestonesHeader(badgeCount: Int, progress: Float) {
        val totalBadges = 11 // Total badges available
        val userName =
                FirebaseAuth.getInstance().currentUser?.displayName?.split(" ")?.firstOrNull()
                        ?: "User"

        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
                Box(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .background(
                                                Brush.horizontalGradient(
                                                        colors = listOf(Purple400, Purple500)
                                                )
                                        )
                                        .padding(20.dp)
                ) {
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                        ) {
                                Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                                text = "$userName!",
                                                color = White,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Medium
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                                text = "$badgeCount Badges",
                                                color = White,
                                                fontSize = 32.sp,
                                                fontWeight = FontWeight.Bold
                                        )

                                        Spacer(modifier = Modifier.height(20.dp))

                                        Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                        ) {
                                                Text(
                                                        text = "Progress",
                                                        color = White.copy(alpha = 0.9f),
                                                        fontSize = 14.sp
                                                )
                                                Text(
                                                        text = "$badgeCount / $totalBadges",
                                                        color = White.copy(alpha = 0.9f),
                                                        fontSize = 14.sp
                                                )
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        LinearProgressIndicator(
                                                progress = { progress },
                                                modifier =
                                                        Modifier.fillMaxWidth()
                                                                .height(6.dp)
                                                                .clip(RoundedCornerShape(3.dp)),
                                                color = White.copy(alpha = 0.9f),
                                                trackColor = White.copy(alpha = 0.3f)
                                        )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                // Circular Progress Indicator
                                Box(
                                        modifier = Modifier.size(64.dp),
                                        contentAlignment = Alignment.Center
                                ) {
                                        Canvas(modifier = Modifier.size(64.dp)) {
                                                val strokeWidth = 6.dp.toPx()

                                                // Background circle
                                                drawArc(
                                                        color = White.copy(alpha = 0.3f),
                                                        startAngle = -90f,
                                                        sweepAngle = 360f,
                                                        useCenter = false,
                                                        style =
                                                                Stroke(
                                                                        width = strokeWidth,
                                                                        cap = StrokeCap.Round
                                                                ),
                                                        size =
                                                                Size(
                                                                        size.width - strokeWidth,
                                                                        size.height - strokeWidth
                                                                ),
                                                        topLeft =
                                                                Offset(
                                                                        strokeWidth / 2,
                                                                        strokeWidth / 2
                                                                )
                                                )

                                                // Progress arc
                                                drawArc(
                                                        color = White,
                                                        startAngle = -90f,
                                                        sweepAngle = 360f * progress,
                                                        useCenter = false,
                                                        style =
                                                                Stroke(
                                                                        width = strokeWidth,
                                                                        cap = StrokeCap.Round
                                                                ),
                                                        size =
                                                                Size(
                                                                        size.width - strokeWidth,
                                                                        size.height - strokeWidth
                                                                ),
                                                        topLeft =
                                                                Offset(
                                                                        strokeWidth / 2,
                                                                        strokeWidth / 2
                                                                )
                                                )
                                        }

                                        Text(
                                                text = "${(progress * 100).toInt()}%",
                                                color = White,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold
                                        )
                                }
                        }
                }
        }
}
