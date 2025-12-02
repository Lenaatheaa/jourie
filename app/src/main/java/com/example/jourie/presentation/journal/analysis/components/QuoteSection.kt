package com.example.jourie.presentation.journal.analysis.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.jourie.ui.theme.LightPurpleBg
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.TextDark

@Composable
fun QuoteSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(LightPurpleBg)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "\"Peace comes from within. Do not seek it without.\"",
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                color = TextDark.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "● ● ● ● ●", color = PrimaryPurple.copy(alpha = 0.4f))
        }
    }
}






