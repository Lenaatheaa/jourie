package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.R // Ganti dengan R dari proyek Anda jika berbeda
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.White
import com.example.jourie.ui.theme.TextDark

@Composable
fun DashboardWelcomeHeader(username: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(White)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Welcome back, $username!",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Let's reflect on your day.",
                color = TextDark.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }

        // Avatar on the right
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), // Placeholder avatar
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(26.dp))
                .border(1.dp, Color(0xFFE6E1EA), RoundedCornerShape(26.dp))
        )
    }
}
