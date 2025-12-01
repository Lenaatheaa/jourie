package com.example.petstreak.data.model

data class PetLevel(
    val level: Int,
    val name: String,
    val requiredDays: Int,
    val daysRemaining: Int,
    val petImageRes: Int? = null,
    val isUnlocked: Boolean = false
)
