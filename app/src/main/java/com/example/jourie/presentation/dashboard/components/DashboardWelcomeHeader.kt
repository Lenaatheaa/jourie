package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun DashboardWelcomeHeader(username: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
            .background(PrimaryPurple)
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Welcome back,",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp
            )
            Text(
                text = "$username!",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), // Placeholder
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(16.dp))
        )
    }
}
