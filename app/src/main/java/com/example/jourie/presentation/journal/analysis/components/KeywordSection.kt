package com.example.jourie.presentation.journal.analysis.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.LightPurpleBg
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.TextDark
import com.example.jourie.ui.theme.White

@Composable
fun KeywordSection(keywords: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Keywords", fontWeight = FontWeight.Bold, color = TextDark)
            Text("keyword", color = PrimaryPurple, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(12.dp)) // Ini sekarang di baris baru

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                keywords.forEach { keyword ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(LightPurpleBg)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(keyword, color = PrimaryPurple, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    }
                }
            }
        }
    }}

@Preview(showBackground = true)
@Composable
private fun KeywordSectionPreview() {
    val sampleKeywords = listOf("grateful", "peaceful", "centered")
    JourieTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            KeywordSection(keywords = sampleKeywords)
        }
    }
}







