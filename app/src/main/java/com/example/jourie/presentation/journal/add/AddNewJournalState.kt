// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/journal/add/AddNewJournalState.kt

package com.example.jourie.presentation.journal.add

// DIPERBAIKI: Semua duplikasi dan kesalahan sintaks telah dihapus.
// Hanya ada satu definisi data class yang benar.
data class AddNewJournalState(
    val content: String = "",
    val currentDate: String = "", // Format: "EEEE, MMMM dd, yyyy"
    val isLoading: Boolean = false,
    val isSaved: Boolean = false
)
