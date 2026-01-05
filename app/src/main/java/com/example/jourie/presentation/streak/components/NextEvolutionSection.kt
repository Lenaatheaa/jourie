// File: .../presentation/streak/components/NextEvolutionSection.kt
package com.example.jourie.presentation.streak.components

// --- IMPORT YANG DIPERLUKAN UNTUK GAMBAR PNG ---
import androidx.compose.foundation.Image // DITAMBAHKAN
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource // DITAMBAHKAN
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.R // DITAMBAHKAN
import com.example.jourie.ui.theme.Gray500
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.Purple100
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

@Composable
fun NextEvolutionSection(
        nextEvolutionName: String,
        daysToEvolve: Int,
        progress: Float,
        currentStreakDays: Int
) {
    Row(
            modifier =
                    Modifier.fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp))
                            .background(White)
                            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
    ) { 
        Image(
                painter = painterResource(id = R.drawable.ccp), 
                contentDescription = "Next Evolution Icon",
                modifier = Modifier.size(40.dp) 
        )
      

        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                        text = "Next Evolution",
                        fontWeight = FontWeight.Bold,
                        color = Gray900,
                        fontSize = 16.sp
                )
                Text(
                        text = "$daysToEvolve days",
                        fontWeight = FontWeight.SemiBold,
                        color = Gray900,
                        fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = nextEvolutionName, color = Gray500, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                    color = Purple500,
                    trackColor = Purple100
            )
            Spacer(modifier = Modifier.height(4.dp))
            val remainingDays = (daysToEvolve - currentStreakDays).coerceAtLeast(0)
            Text(
                    text =
                            if (remainingDays > 0) {
                                "$remainingDays more days to evolve!"
                            } else {
                                "Ready to evolve!"
                            },
                    color = Gray500,
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
                progress = 0.25f,
                currentStreakDays = 1
        )
    }
}
