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
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.PrimaryPurplePastel
import com.example.jourie.ui.theme.TextDark

@Composable
fun QuoteSection(quoteText: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(PrimaryPurplePastel)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = quoteText,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                color = TextDark.copy(alpha = 0.85f)
            )
            Spacer(modifier = Modifier.height(12.dp))

            // indicator dots centered with spacing
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.weight(1f))
                repeat(5) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(PrimaryPurple.copy(alpha = 0.45f), shape = RoundedCornerShape(6.dp))
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}






