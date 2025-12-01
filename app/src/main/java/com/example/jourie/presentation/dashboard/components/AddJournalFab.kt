package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.jourie.ui.theme.PrimaryPurple

@Composable
fun AddJournalFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        containerColor = PrimaryPurple,
        contentColor = Color.White
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add Journal")
    }
}
