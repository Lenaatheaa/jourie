package com.example.jourie.presentation.achievements.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

@Composable
fun ShareMilestonesButton(onClick: () -> Unit) {
    Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Purple500)
    ) {
        Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = White)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Share", color = White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}
