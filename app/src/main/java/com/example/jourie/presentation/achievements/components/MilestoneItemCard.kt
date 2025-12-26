package com.example.jourie.presentation.achievements.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.data.model.Badge
import com.example.jourie.ui.theme.Gray400
import com.example.jourie.ui.theme.Gray50
import com.example.jourie.ui.theme.Gray500
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.Purple100
import com.example.jourie.ui.theme.Purple600
import com.example.jourie.ui.theme.White

@Composable
fun MilestoneItemCard(badge: Badge) {
    val backgroundColor = if (badge.isUnlocked) Purple100 else Gray50
    val textColor = if (badge.isUnlocked) Gray900 else Gray400
    val subtitleColor = if (badge.isUnlocked) Purple600 else Gray400

    Card(
            modifier = Modifier.height(140.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
                modifier = Modifier.fillMaxSize().padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            if (badge.isUnlocked) {
                // Unlocked badge with large icon in white circle
                Box(
                        modifier =
                                Modifier.size(56.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(White),
                        contentAlignment = Alignment.Center
                ) { Text(text = badge.icon, fontSize = 30.sp) }
            } else {
                // Locked badge with lock icon in gray circle
                Box(
                        modifier = Modifier.size(56.dp).clip(CircleShape).background(Gray500),
                        contentAlignment = Alignment.Center
                ) {
                    Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = White,
                            modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                    text = badge.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = textColor,
                    textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                    text = badge.subtitle,
                    fontSize = 12.sp,
                    color = subtitleColor,
                    textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MilestoneItemCardPreview() {
    val sampleBadgeUnlocked = Badge(1, "Level 1", "3 days in a row", "Streak", true, "🔥")
    val sampleBadgeLocked = Badge(3, "Level 3", "14 days in a row", "Streak", false, "🔒")

    JourieTheme {
        Row {
            Column(Modifier.weight(1f).padding(8.dp)) {
                MilestoneItemCard(badge = sampleBadgeUnlocked)
            }
            Column(Modifier.weight(1f).padding(8.dp)) {
                MilestoneItemCard(badge = sampleBadgeLocked)
            }
        }
    }
}
