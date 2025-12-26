package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.White

@Composable
fun AddJournalFab(onClick: () -> Unit) {
    FloatingActionButton(
            onClick = onClick,
            shape = CircleShape,
            containerColor = Purple400,
            contentColor = White
    ) { Icon(Icons.Default.Add, contentDescription = "Add Journal") }
}
