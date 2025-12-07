// File: .../presentation/streak/components/EvolutionTimelineSection.kt
package com.example.jourie.presentation.streak.components

// --- IMPORT YANG DIPERLUKAN UNTUK GAMBAR PNG ---
import androidx.compose.foundation.Image // DITAMBAHKAN
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
import androidx.compose.ui.res.painterResource // DITAMBAHKAN
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.R                 // DITAMBAHKAN
import com.example.jourie.data.model.EvolutionStage
import com.example.jourie.ui.theme.*

// --- KODE LENGKAP UNTUK EVOLUTION TIMELINE DENGAN PNG ---

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

    // --- Logika untuk memilih gambar berdasarkan nama ---
    // Logika ini memilih resource gambar dari drawable berdasarkan nama stage
    val imageRes = when (stage.name.lowercase()) {
        "capybara" -> R.drawable.ccp // Ganti dengan nama file PNG Anda
        "bear" -> R.drawable.ccp       // Ganti dengan nama file PNG Anda
        "dragon" -> R.drawable.ccp     // Ganti dengan nama file PNG Anda
        else -> R.drawable.ccp // Gambar default jika nama tidak cocok
    }
    // ----------------------------------------------------

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)) // Bentuk Card
            .background(backgroundColor)
            .border(borderWidth, borderColor, RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- DIPERBAIKI: Mengganti Text emoji dengan Image untuk PNG ---
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = stage.name,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape) // Membuat gambar menjadi lingkaran
        )
        // -------------------------------------------------------------

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

        // Tanda centang jika sudah tercapai (TETAP SAMA)
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
        EvolutionStage(level = 2, name = "Bear", daysRequired = 7, isAchieved = false),
        EvolutionStage(level = 3, name = "Dragon", daysRequired = 14, isAchieved = false),
    )
    JourieTheme {
        EvolutionTimelineSection(timeline = timeline)
    }
}
