package com.example.jourie.presentation.journal.analysis.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.Purple50
import com.example.jourie.ui.theme.Purple700

@Composable
fun QuoteSection(quoteText: String) {
        Box(
                modifier =
                        Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .background(Purple50) // Very light purple/pink background
                                .padding(20.dp)
        ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                        // Quote icon
                        Icon(
                                imageVector = Icons.Default.FormatQuote,
                                contentDescription = "Quote",
                                tint = Purple400,
                                modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        // Quote text
                        Text(
                                text = quoteText,
                                fontStyle = FontStyle.Italic,
                                color = Purple700,
                                fontSize = 15.sp,
                                lineHeight = 22.sp,
                                modifier = Modifier.weight(1f)
                        )
                }
        }
}
