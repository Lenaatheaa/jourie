package com.example.jourie.presentation.streak.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.*

@Composable
fun NextEvolutionSection(
    nextEvolutionName: String,
    daysToEvolve: Int,
    progress: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "👑", fontSize = 28.sp, modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Next Evolution",
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    fontSize = 16.sp
                )
                Text(
                    text = "$daysToEvolve days",
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = nextEvolutionName,
                color = IconGray,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = PrimaryPurple,
                trackColor = PrimaryPurpleLight
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${daysToEvolve - 3} more days to evolve!", // Logika dummy
                color = IconGray,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
private fun NextEvolutionSectionPreview() {
    JourieTheme {
        NextEvolutionSection(
            nextEvolutionName = "Capybara",
            daysToEvolve = 4,
            progress = 0.25f
        )
    }
}
