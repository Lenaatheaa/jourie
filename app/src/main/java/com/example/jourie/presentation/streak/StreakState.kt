package com.example.jourie.presentation.streak

import com.example.jourie.data.model.StreakData

data class StreakState(
    val isLoading: Boolean = true,
    val streakData: StreakData? = null,
    val evolutionProgress: Float = 0f,
    val error: String? = null
)
