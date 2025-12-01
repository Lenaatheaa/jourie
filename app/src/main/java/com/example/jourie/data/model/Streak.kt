package com.example.petstreak.data.model

data class Streak(
    val currentDayStreak: Int = 15,
    val totalDaysActive: Int = 45,
    val currentLevel: Int = 1,
    val recentStreakDays: List<Boolean> = List(8) { true },
    val evolutionProgress: Int = 3
)
