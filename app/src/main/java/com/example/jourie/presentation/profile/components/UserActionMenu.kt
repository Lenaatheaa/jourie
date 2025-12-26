package com.example.jourie.presentation.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Gray200
import com.example.jourie.ui.theme.Gray400
import com.example.jourie.ui.theme.Gray500
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.Red50
import com.example.jourie.ui.theme.Red500
import com.example.jourie.ui.theme.White

@Composable
fun ProfileMenuSection(
        onSettingsClick: () -> Unit = {},
        onHelpClick: () -> Unit = {},
        onPrivacyClick: () -> Unit = {},
        onAboutClick: () -> Unit = {},
        isDarkMode: Boolean = false,
        onDarkModeToggle: (Boolean) -> Unit = {},
        onLogoutClick: () -> Unit,
        onDeleteAccountClick: () -> Unit = {}
) {
        Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
                // Menu items card
                Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(16.dp)
                ) {
                        Column {
                                MenuItem(
                                        icon = Icons.Default.Settings,
                                        text = "Settings",
                                        onClick = onSettingsClick
                                )
                                HorizontalDivider(color = Gray200)

                                MenuItem(
                                        icon = Icons.Default.HelpOutline,
                                        text = "Help & Support",
                                        onClick = onHelpClick
                                )
                                HorizontalDivider(color = Gray200)

                                MenuItem(
                                        icon = Icons.Default.Shield,
                                        text = "Privacy Policy",
                                        onClick = onPrivacyClick
                                )
                                HorizontalDivider(color = Gray200)

                                MenuItem(
                                        icon = Icons.Default.Info,
                                        text = "About App",
                                        onClick = onAboutClick
                                )
                                HorizontalDivider(color = Gray200)

                                // Dark Mode dengan toggle
                                Row(
                                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                        Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                                Icon(
                                                        imageVector = Icons.Default.DarkMode,
                                                        contentDescription = null,
                                                        tint = Gray900
                                                )
                                                Text("Dark Mode", fontSize = 16.sp, color = Gray900)
                                        }
                                        Switch(
                                                checked = isDarkMode,
                                                onCheckedChange = onDarkModeToggle,
                                                colors =
                                                        SwitchDefaults.colors(
                                                                checkedThumbColor = Color.White,
                                                                checkedTrackColor = Purple500,
                                                                uncheckedThumbColor = Color.White,
                                                                uncheckedTrackColor = Gray200
                                                        )
                                        )
                                }
                        }
                }

                // Log Out button
                Card(
                        modifier = Modifier.fillMaxWidth().clickable { onLogoutClick() },
                        colors = CardDefaults.cardColors(containerColor = White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(16.dp)
                ) {
                        Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                                Icon(
                                        imageVector = Icons.Default.Logout,
                                        contentDescription = null,
                                        tint = Red500
                                )
                                Text(
                                        "Log Out",
                                        fontSize = 16.sp,
                                        color = Red500,
                                        fontWeight = FontWeight.Medium
                                )
                        }
                }

                // Danger Zone
                Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors =
                                CardDefaults.cardColors(
                                        containerColor = Red50 // Light pink
                                ),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(16.dp)
                ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                        Icon(
                                                imageVector = Icons.Default.Warning,
                                                contentDescription = null,
                                                tint = Red500,
                                                modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                                "Danger Zone",
                                                fontSize = 16.sp,
                                                color = Red500,
                                                fontWeight = FontWeight.Bold
                                        )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                        "Deleting your account is permanent and cannot be undone. All your data will be lost.",
                                        fontSize = 13.sp,
                                        color = Gray500,
                                        lineHeight = 18.sp
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedButton(
                                        onClick = onDeleteAccountClick,
                                        modifier = Modifier.fillMaxWidth(),
                                        colors =
                                                ButtonDefaults.outlinedButtonColors(
                                                        contentColor = Red500
                                                ),
                                        border = BorderStroke(1.dp, Red500),
                                        shape = RoundedCornerShape(12.dp)
                                ) {
                                        Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                                "Delete Account",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Medium
                                        )
                                }
                        }
                }
        }
}

@Composable
private fun MenuItem(icon: ImageVector, text: String, onClick: () -> Unit) {
        Row(
                modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                        Icon(imageVector = icon, contentDescription = null, tint = Gray900)
                        Text(text, fontSize = 16.sp, color = Gray900)
                }
                Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Gray400
                )
        }
}

@Preview(showBackground = true)
@Composable
private fun ProfileMenuSectionPreview() {
        JourieTheme { ProfileMenuSection(onLogoutClick = {}) }
}
