package com.example.jourie.presentation.achievements.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.White
import com.example.jourie.ui.theme.TextDark

@Composable
fun CtaUnlockCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(White)
            .border(BorderStroke(1.dp, PrimaryPurple), RoundedCornerShape(10.dp))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Keep journaling to unlock more achievements!",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = TextDark,
                textAlign = TextAlign.Center
            )
            Text(text = "✨✨✨✨✨", fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))
        }
    }
}
