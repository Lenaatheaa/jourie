// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/journal/add/AddNewJournalState.kt

package com.example.jourie.presentation.journal.add


data class AddNewJournalState(
    val content: String = "",
    val currentDate: String = "", // Format: "EEEE, MMMM dd, yyyy"
    val mood: String = "", // Mood yang dipilih user
    val isLoading: Boolean = false,
    val isSaved: Boolean = false
)
