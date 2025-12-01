package com.example.jourie.presentation.achievements

import com.example.jourie.data.model.Badge

// State dengan nama unik
data class MilestonesState(
    val isLoading: Boolean = true,
    val streakBadges: List<Badge> = emptyList(),
    val journalBadges: List<Badge> = emptyList(),
    val progressPercent: Float = 0f,
    val badgeCount: Int = 0,
    val error: String? = null
)
