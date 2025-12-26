package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Gray500
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.White

@Composable
fun DashboardWelcomeHeader(username: String) {
        Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
                Column(modifier = Modifier.weight(1f)) {
                        Text(
                                text = "Good Morning, $username! ✨",
                                color = Gray900,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                                text = "Let's reflect on your day ✨",
                                color = Gray500,
                                fontSize = 13.sp
                        )
                }

                // Avatar button with initial
                Box(
                        modifier =
                                Modifier.size(54.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Purple400),
                        contentAlignment = Alignment.Center
                ) {
                        Text(
                                text = username.firstOrNull()?.uppercase() ?: "A",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold
                        )
                }
        }
}
