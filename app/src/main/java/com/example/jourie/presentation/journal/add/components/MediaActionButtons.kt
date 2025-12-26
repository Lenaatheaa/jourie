package com.example.jourie.presentation.journal.add.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Blue500
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

@Composable
fun MediaActionButtons(modifier: Modifier = Modifier) {
        Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
                // Voice Input - Blue
                ActionButton(
                        text = "Voice Input",
                        icon = Icons.Default.Mic,
                        backgroundColor = Blue500, // Blue
                        onClick = { /*TODO*/},
                        modifier = Modifier.weight(1f)
                )

                // Add Image - Purple
                ActionButton(
                        text = "Add Image",
                        icon = Icons.Default.Image,
                        backgroundColor = Purple500,
                        onClick = { /*TODO*/},
                        modifier = Modifier.weight(1f)
                )
        }
}

@Composable
private fun ActionButton(
        text: String,
        icon: ImageVector,
        backgroundColor: Color,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
) {
        Button(
                onClick = onClick,
                modifier = modifier.height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors =
                        ButtonDefaults.buttonColors(
                                containerColor = backgroundColor,
                                contentColor = White
                        ),
                elevation =
                        ButtonDefaults.buttonElevation(
                                defaultElevation = 2.dp,
                                pressedElevation = 4.dp
                        )
        ) {
                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                ) {
                        Icon(
                                imageVector = icon,
                                contentDescription = text,
                                tint = White,
                                modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = text, color = White, fontSize = 14.sp)
                }
        }
}
