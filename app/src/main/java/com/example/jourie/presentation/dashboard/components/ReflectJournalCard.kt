package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

@Composable
fun ReflectJournalCard(onWriteClick: () -> Unit) {
        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
                Column(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .background(
                                                Brush.horizontalGradient(
                                                        colors = listOf(Purple400, Purple500)
                                                )
                                        )
                                        .padding(20.dp)
                ) {
                        Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                        ) {
                                Text(
                                        text = "✨ Reflect on your day",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = White
                                )
                                Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        tint = Color.White.copy(alpha = 0.8f),
                                        modifier = Modifier.size(24.dp)
                                )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                                text =
                                        "How are you feeling today? Share your thoughts and emotions.",
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                                onClick = onWriteClick,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = White),
                                modifier = Modifier.fillMaxWidth()
                        ) {
                                Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null,
                                        tint = Purple400,
                                        modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                        text = "Write Journal",
                                        color = Purple400,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 15.sp
                                )
                        }
                }
        }
}
