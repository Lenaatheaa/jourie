package com.example.jourie.data.model

import androidx.compose.ui.graphics.Color

// Menggunakan nama EmotionSnapshot untuk fitur baru
data class EmotionSnapshot(
    val name: String,
    val percentage: Int,
    val change: String, // e.g., "+2%" or "-3%"
    val color: Color
)
