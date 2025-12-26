package com.example.jourie.presentation.streak.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White
import java.util.Calendar

@Composable
fun RecentStreakDaysSection() {
        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
                Column(
                        modifier =
                                Modifier.background(
                                                Brush.horizontalGradient(
                                                        colors = listOf(Purple400, Purple500)
                                                )
                                        )
                                        .padding(20.dp)
                ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                        imageVector = Icons.Default.LocalFireDepartment,
                                        contentDescription = "Streak",
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                        text = "Recent Streak Days",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = Color.White
                                )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                                val days = listOf("M", "T", "W", "T", "F", "S", "S")
                                val calendar = Calendar.getInstance()
                                val activeIndex =
                                        when (calendar.get(Calendar.DAY_OF_WEEK)) {
                                                Calendar.MONDAY -> 0
                                                Calendar.TUESDAY -> 1
                                                Calendar.WEDNESDAY -> 2
                                                Calendar.THURSDAY -> 3
                                                Calendar.FRIDAY -> 4
                                                Calendar.SATURDAY -> 5
                                                Calendar.SUNDAY -> 6
                                                else -> 0
                                        }

                                days.forEachIndexed { index, day ->
                                        Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                                Box(
                                                        modifier =
                                                                Modifier.size(46.dp)
                                                                        .clip(
                                                                                RoundedCornerShape(
                                                                                        16.dp
                                                                                )
                                                                        )
                                                                        .background(
                                                                                Color.White.copy(
                                                                                        alpha =
                                                                                                if (index == activeIndex) 0.4f
                                                                                                else 0.25f
                                                                                )
                                                                        ),
                                                        contentAlignment = Alignment.Center
                                                ) {
                                                        Icon(
                                                                imageVector =
                                                                        Icons.Default
                                                                                .LocalFireDepartment,
                                                                contentDescription = null,
                                                                tint = Color.White,
                                                                modifier = Modifier.size(22.dp)
                                                        )
                                                }
                                                Text(
                                                        text = day,
                                                        fontWeight =
                                                                if (index == activeIndex)
                                                                        FontWeight.ExtraBold
                                                                else FontWeight.Normal,
                                                        color = Color.White,
                                                        fontSize = 13.sp
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
        JourieTheme { RecentStreakDaysSection() }
}
