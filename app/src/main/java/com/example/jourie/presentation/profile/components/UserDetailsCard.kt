package com.example.jourie.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Blue500
import com.example.jourie.ui.theme.Gray400
import com.example.jourie.ui.theme.Gray500
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.Green500
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.Orange500
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

@Composable
fun UserDetailsCard(
        name: String,
        email: String,
        phone: String,
        dob: String,
        onEditClick: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current

    Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header dengan edit button
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                        text = "Personal Detail",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Purple500,
                            modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Email
            InfoRow(
                    icon = Icons.Default.Email,
                    iconColor = Blue500, // Blue
                    label = "Email",
                    value = email,
                    showCopy = true,
                    onCopy = { clipboardManager.setText(AnnotatedString(email)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Phone
            InfoRow(
                    icon = Icons.Default.Phone,
                    iconColor = Green500, // Green
                    label = "Phone",
                    value = phone.ifBlank { "Not set" },
                    showCopy = phone.isNotBlank(),
                    onCopy = { clipboardManager.setText(AnnotatedString(phone)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date of Birth
            InfoRow(
                    icon = Icons.Default.CalendarToday,
                    iconColor = Orange500, // Orange
                    label = "Date of Birth",
                    value = dob.ifBlank { "Not set" },
                    showCopy = false
            )
        }
    }
}

@Composable
private fun InfoRow(
        icon: ImageVector,
        iconColor: Color,
        label: String,
        value: String,
        showCopy: Boolean,
        onCopy: () -> Unit = {}
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        // Colored icon circle
        Box(
                modifier = Modifier.size(48.dp).background(iconColor, CircleShape),
                contentAlignment = Alignment.Center
        ) {
            Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Label and value
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontSize = 12.sp, color = Gray500)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, fontSize = 15.sp, color = Gray900, fontWeight = FontWeight.Medium)
        }

        // Copy button
        if (showCopy) {
            IconButton(onClick = onCopy, modifier = Modifier.size(40.dp)) {
                Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        tint = Gray400,
                        modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserDetailsCardPreview() {
    JourieTheme {
        UserDetailsCard(
                name = "Admin Aja",
                email = "admin@gmail.com",
                phone = "081214776579",
                dob = "12 Dec 2000",
                onEditClick = {}
        )
    }
}
