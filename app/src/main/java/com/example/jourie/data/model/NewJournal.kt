package com.example.jourie.data.model

// Menggunakan nama NewJournal untuk menghindari konflik
data class NewJournal(
    val id: Long = 0L,
    val content: String,
    val dateTimestamp: Long,
    val mood: String = "Neutral"
)
