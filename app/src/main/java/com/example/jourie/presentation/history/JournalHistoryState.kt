package com.example.jourie.presentation.history

import com.example.jourie.data.model.JournalEntry

// State dengan nama unik
data class JournalHistoryState(
    val isLoading: Boolean = true,
    val searchQuery: String = "",
    val allJournals: List<JournalEntry> = emptyList(), // Sumber data asli
    val filteredJournals: List<JournalEntry> = emptyList(), // Data yang ditampilkan
    val error: String? = null
)
