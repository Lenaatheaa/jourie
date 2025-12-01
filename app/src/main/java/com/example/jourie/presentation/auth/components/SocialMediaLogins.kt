package com.example.jourie.presentation.auth.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.R // Ganti dengan R dari proyek Anda
import com.example.jourie.ui.theme.TextDark

// --- 2. SocialMediaLogins ---
@Composable
fun SocialMediaLogins() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("atau masuk dengan", color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            SocialButton(
                iconId = R.drawable.ic_launcher_background, // Ganti dengan ikon Google Anda
                onClick = {}
            )
            SocialButton(
                iconId = R.drawable.ic_launcher_background, // Ganti dengan ikon Apple/Facebook Anda
                onClick = {}
            )
        }
    }
}

@Composable
private fun SocialButton(iconId: Int, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.size(60.dp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, Color.LightGray),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "Social Login",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
    }
}
