package com.example.jourie.presentation.journal.add.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

@Composable
fun SaveEntryButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
        Button(
                onClick = onClick,
                modifier = modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(14.dp),
                colors =
                        ButtonDefaults.buttonColors(
                                containerColor = Purple500,
                                contentColor = White
                        ),
                elevation =
                        ButtonDefaults.buttonElevation(
                                defaultElevation = 2.dp,
                                pressedElevation = 4.dp
                        )
        ) { Text(text = "Save & Reflect", fontWeight = FontWeight.SemiBold, fontSize = 16.sp) }
}
