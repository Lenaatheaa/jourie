package com.example.jourie.presentation.streak.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.BorderGray
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.TextDark
import com.example.jourie.ui.theme.White
import com.example.jourie.ui.theme.PrimaryPurplePastel

@Composable
fun PetDisplaySection(level: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(PrimaryPurplePastel, RoundedCornerShape(16.dp))
            .border(1.dp, BorderGray, RoundedCornerShape(16.dp))
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🐹", // Dummy asset
                fontSize = 100.sp // biarkan ukuran font mengatur ukuran emoji
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "✨ Level $level",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }
    }
}

@Preview
@Composable
private fun PetDisplaySectionPreview() {
    JourieTheme {
        PetDisplaySection(level = 1)
    }
}
