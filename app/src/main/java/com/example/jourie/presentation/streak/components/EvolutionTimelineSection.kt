package com.example.jourie.presentation.streak.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.data.model.EvolutionStage
import com.example.jourie.ui.theme.*

@Composable
fun EvolutionTimelineSection(timeline: List<EvolutionStage>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Evolution Timeline",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            timeline.forEach { stage ->
                EvolutionTimelineItem(stage)
            }
        }
    }
}

@Composable
private fun EvolutionTimelineItem(stage: EvolutionStage) {
    val backgroundColor = if (stage.isAchieved) PrimaryPurplePastel else White
    val borderColor = if (stage.isAchieved) PrimaryPurple else BorderGrayLight
    val borderWidth = if (stage.isAchieved) 1.5.dp else 1.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .border(borderWidth, borderColor, RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "🐹", fontSize = 32.sp, modifier = Modifier.size(50.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${stage.name} Level ${stage.level}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextDark
            )
            Text(
                text = "${stage.daysRequired} days",
                color = IconGray,
                fontSize = 14.sp
            )
        }
        if (stage.isAchieved) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(GreenCheck),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "✓", color = White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F2FA)
@Composable
private fun EvolutionTimelineSectionPreview() {
    val timeline = listOf(
        EvolutionStage(level = 1, name = "Capybara", daysRequired = 3, isAchieved = true),
        EvolutionStage(level = 2, name = "Capybara", daysRequired = 7, isAchieved = false),
        EvolutionStage(level = 3, name = "Bear", daysRequired = 14, isAchieved = false),
    )
    JourieTheme {
        EvolutionTimelineSection(timeline = timeline)
    }
}
