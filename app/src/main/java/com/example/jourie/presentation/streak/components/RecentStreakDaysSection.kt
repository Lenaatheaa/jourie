package com.example.jourie.presentation.streak.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.IconGray
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.TextDark
import com.example.jourie.ui.theme.White

@Composable
fun RecentStreakDaysSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = "Streak",
                    tint = Color(0xFFFF7043),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Recent Streak Days",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextDark
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val days = listOf("M", "T", "W", "T", "F", "S", "S")
                val activeIndex = 1 // match dashboard look

                days.forEachIndexed { index, day ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(PrimaryPurple),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = day,
                            fontWeight = if (index == activeIndex) FontWeight.Bold else FontWeight.Normal,
                            color = if (index == activeIndex) PrimaryPurple else IconGray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F2FA)
@Composable
private fun RecentStreakDaysSectionPreview() {
    JourieTheme {
        RecentStreakDaysSection()
    }
}
