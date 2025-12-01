package com.example.jourie.data.model

// Menggunakan nama Badge untuk menghindari konflik
data class Badge(
    val id: Int,
    val title: String,
    val subtitle: String,
    val category: String, // "Streak" or "Journal"
    val isUnlocked: Boolean,
    val icon: String // emoji icon
)
