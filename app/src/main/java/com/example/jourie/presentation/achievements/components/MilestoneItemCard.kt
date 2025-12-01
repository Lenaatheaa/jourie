// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/achievements/components/MilestoneItemCard.kt

package com.example.jourie.presentation.achievements.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.data.model.Badge
import com.example.jourie.ui.theme.IconGray
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.PrimaryPurplePastel
import com.example.jourie.ui.theme.TextDark
import com.example.jourie.ui.theme.White

@Composable
fun MilestoneItemCard(badge: Badge) {
    val backgroundColor = if (badge.isUnlocked) PrimaryPurplePastel else White
    val contentAlpha = if (badge.isUnlocked) 1f else 0.4f

    Card(
        modifier = Modifier.alpha(contentAlpha),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(1.dp, Color(0xFFE5CCFF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = badge.icon, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = badge.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = TextDark,
                textAlign = TextAlign.Center
            )
            Text(
                text = badge.subtitle,
                fontSize = 12.sp,
                color = if (badge.isUnlocked) PrimaryPurple else IconGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Preview ditambahkan untuk memudahkan pengecekan UI
@Preview(showBackground = true)
@Composable
private fun MilestoneItemCardPreview() {
    val sampleBadgeUnlocked = Badge(1, "Streak 1", "3 days", "Streak", true, "🔥")
    val sampleBadgeLocked = Badge(3, "Streak 3", "14 days", "Streak", false, "🔒")

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
