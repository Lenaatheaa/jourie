package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Blue500
import com.example.jourie.ui.theme.Gray200
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.Green500
import com.example.jourie.ui.theme.Orange500
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

@Composable
fun AchievementsProgressCard(progress: Map<String, Int>) {
        Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                        Box(
                                modifier =
                                        Modifier.size(40.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(Purple500),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        imageVector = Icons.Default.EmojiEvents,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                )
                        }
                        Text(
                                text = "Achievements",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Gray900
                        )
                }

                Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                        Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                                AchievementItem(
                                        icon = Icons.Default.EmojiEvents,
                                        iconColor = Orange500,
                                        label = "Streak",
                                        progress = (progress["Streak"] ?: 0) / 100f
                                )
                                AchievementItem(
                                        icon = Icons.Outlined.FavoriteBorder,
                                        iconColor = Blue500,
                                        label = "Emotions",
                                        progress = (progress["Emotions"] ?: 0) / 100f
                                )
                                AchievementItem(
                                        icon = Icons.Default.MenuBook,
                                        iconColor = Green500,
                                        label = "Journals",
                                        progress = (progress["Journals"] ?: 0) / 100f
                                )
                        }
                }
        }
}

@Composable
private fun AchievementItem(icon: ImageVector, iconColor: Color, label: String, progress: Float) {
        Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
                Box(
                        modifier =
                                Modifier.size(40.dp)
                                        .clip(CircleShape)
                                        .background(iconColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                ) {
                        Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = iconColor,
                                modifier = Modifier.size(20.dp)
                        )
                }

                Column(modifier = Modifier.weight(1f)) {
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                                Text(
                                        text = label,
                                        fontSize = 14.sp,
                                        color = Gray900,
                                        fontWeight = FontWeight.Medium
                                )
                                Text(
                                        text = "${(progress * 100).toInt()}%",
                                        fontSize = 14.sp,
                                        color = iconColor,
                                        fontWeight = FontWeight.SemiBold
                                )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        LinearProgressIndicator(
                                progress = { progress },
                                modifier =
                                        Modifier.fillMaxWidth()
                                                .height(6.dp)
                                                .clip(RoundedCornerShape(3.dp)),
                                color = iconColor,
                                trackColor = Gray200
                        )
                }
        }
}
