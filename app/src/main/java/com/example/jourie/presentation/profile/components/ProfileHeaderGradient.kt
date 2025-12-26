package com.example.jourie.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
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
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

@Composable
fun ProfileHeaderGradient(
        name: String,
        memberSince: String = "Member since Dec 2024",
        onCameraClick: () -> Unit = {}
) {
        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
                Box(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .background(
                                                Brush.verticalGradient(
                                                        colors = listOf(Purple400, Purple500)
                                                )
                                        )
                                        .padding(vertical = 48.dp)
                ) {
                        Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                        ) {
                                // Avatar dengan initial
                                Box(contentAlignment = Alignment.BottomEnd) {
                                        // Circle putih besar dengan initial
                                        Box(
                                                modifier =
                                                        Modifier.size(120.dp)
                                                                .background(White, CircleShape),
                                                contentAlignment = Alignment.Center
                                        ) {
                                                Text(
                                                        text =
                                                                name.firstOrNull()
                                                                        ?.uppercaseChar()
                                                                        ?.toString()
                                                                        ?: "A",
                                                        fontSize = 56.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Purple500
                                                )
                                        }

                                        // Camera icon di pojok kanan bawah
                                        Box(
                                                modifier =
                                                        Modifier.offset(x = (-4).dp, y = (-4).dp)
                                                                .size(40.dp)
                                                                .background(Purple500, CircleShape)
                                                                .clickable { onCameraClick() },
                                                contentAlignment = Alignment.Center
                                        ) {
                                                Icon(
                                                        imageVector = Icons.Default.CameraAlt,
                                                        contentDescription = "Change Photo",
                                                        tint = White,
                                                        modifier = Modifier.size(22.dp)
                                                )
                                        }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Username
                                Text(
                                        text = name,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = White
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                // Member since
                                Text(
                                        text = memberSince,
                                        fontSize = 14.sp,
                                        color = White.copy(alpha = 0.9f)
                                )
                        }
                }
        }
}

@Preview(showBackground = true)
@Composable
private fun ProfileHeaderGradientPreview() {
        JourieTheme {
                ProfileHeaderGradient(name = "Admin Aja", memberSince = "Member since Dec 2024")
        }
}
