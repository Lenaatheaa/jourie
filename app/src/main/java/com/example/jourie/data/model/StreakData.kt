package com.example.jourie.data.model

data class StreakData(
    val currentDayStreak: Int,
    val currentPetLevel: Int,
    val daysForCurrentLevel: Int, // Hari yang sudah dijalani di level saat ini
    val nextEvolution: EvolutionStage,
    val timeline: List<EvolutionStage>
)
