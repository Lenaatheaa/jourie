package com.example.jourie.presentation.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

@Composable
fun HistoryPageHeader(totalEntries: Int = 0) {
        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
                Box(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .background(
                                                Brush.horizontalGradient(
                                                        colors = listOf(Purple400, Purple500)
                                                )
                                        )
                                        .padding(horizontal = 20.dp, vertical = 32.dp)
                ) {
                        // Left side: Icon, Title, Subtitle
                        Row(
                                modifier = Modifier.align(Alignment.CenterStart),
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                                Icon(
                                        imageVector = Icons.Default.Book,
                                        contentDescription = "Journal Icon",
                                        tint = White,
                                        modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                        Text(
                                                text = "Journal History",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = White
                                        )
                                        Text(
                                                text = "Your reflection journey",
                                                fontSize = 13.sp,
                                                color = White.copy(alpha = 0.9f)
                                        )
                                }
                        }

                        // Right side: Total entries counter
                        Column(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                horizontalAlignment = Alignment.End
                        ) {
                                Text(
                                        text = totalEntries.toString(),
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = White
                                )
                                Text(
                                        text = "Total Entries",
                                        fontSize = 11.sp,
                                        color = White.copy(alpha = 0.85f)
                                )
                        }
                }
        }
}

@Preview(showBackground = true)
@Composable
private fun HistoryPageHeaderPreview() {
        HistoryPageHeader(totalEntries = 3)
}
