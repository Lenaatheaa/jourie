package com.example.jourie.data.model

// Menggunakan nama JournalEntry untuk menghindari konflik
data class JournalEntry(
    val id: String,
    val dateLabel: String,      // e.g., Today, Yesterday
    val dayOfMonth: Int,        // e.g., 24
    val monthAbbreviation: String, // e.g., Nov
    val mood: String,
    val description: String,
    val tags: List<String>
)
