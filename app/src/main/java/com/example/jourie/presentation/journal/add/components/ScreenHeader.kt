package com.example.jourie.presentation.journal.add.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

@Composable
fun ScreenHeader(currentDate: String, onBackClick: () -> Unit) {
        Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(24.dp),
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
                                        .padding(20.dp)
                ) {
                        // Back Button
                        Box(
                                modifier =
                                        Modifier.align(Alignment.CenterStart)
                                                .size(36.dp)
                                                .background(White.copy(alpha = 0.3f), CircleShape)
                                                .clickable(onClick = onBackClick),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back",
                                        tint = White,
                                        modifier = Modifier.size(20.dp)
                                )
                        }

                        // Date Display with Calendar Icon
                        Row(
                                modifier = Modifier.align(Alignment.Center),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                        ) {
                                Icon(
                                        imageVector = Icons.Default.CalendarToday,
                                        contentDescription = "Calendar",
                                        tint = White,
                                        modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        // Parse date to show day and full date separately
                                        val parts = currentDate.split(", ")
                                        if (parts.size >= 2) {
                                                Text(
                                                        text = parts[0], // Thursday
                                                        color = White,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 16.sp
                                                )
                                                Text(
                                                        text =
                                                                parts.drop(1)
                                                                        .joinToString(
                                                                                ", "
                                                                        ), // December 25, 2025
                                                        color = White.copy(alpha = 0.9f),
                                                        fontSize = 13.sp
                                                )
                                        } else {
                                                Text(
                                                        text = currentDate,
                                                        color = White,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 16.sp
                                                )
                                        }
                                }
                        }
                }
        }
}
