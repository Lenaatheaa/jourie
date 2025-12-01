// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/profile/components/UserActionMenu.kt

package com.example.jourie.presentation.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Logout // DIPERBAIKI: Import sekarang akan berfungsi
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.*

// Menu Aksi dengan nama unik
@Composable
fun UserActionMenu(onLogoutClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            ActionItem(icon = Icons.Default.Settings, text = "Settings", onClick = {})
            Divider(color = BorderGray, thickness = 1.dp)
            ActionItem(
                // DIPERBAIKI: Panggilan ikon kembali normal
                icon = Icons.Default.Logout,
                text = "Log Out",
                color = Color(0xFFFF5757),
                onClick = onLogoutClick
            )
        }
    }
}

@Composable
private fun ActionItem(icon: ImageVector, text: String, color: Color = TextDark, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = color)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = color,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = IconGray,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserActionMenuPreview(){
    UserActionMenu(onLogoutClick = {})
}
