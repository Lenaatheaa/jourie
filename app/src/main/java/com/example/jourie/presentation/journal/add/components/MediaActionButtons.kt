package com.example.jourie.presentation.journal.add.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jourie.ui.theme.LightPurpleBg
import com.example.jourie.ui.theme.PrimaryPurple

@Composable
fun MediaActionButtons(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActionButton(
            text = "Voice Input",
            icon = Icons.Default.Mic,
            onClick = { /*TODO*/ },
            modifier = Modifier.weight(1f)
        )
        ActionButton(
            text = "Add Image",
            icon = Icons.Default.Image,
            onClick = { /*TODO*/ },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF666666)
        ),
        border = BorderStroke(1.dp, Color(0xFFBDBDBD))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = text, tint = Color(0xFF666666))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color(0xFF666666))
        }
    }
}
