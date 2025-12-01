package com.example.jourie.domain.usecase

import com.example.jourie.data.model.Badge

// UseCase dengan nama unik
class GetUserMilestonesUseCase {
    operator fun invoke(badges: List<Badge>): MilestoneCalculationResult {
        val unlockedCount = badges.count { it.isUnlocked }
        val totalCount = badges.size
        val progress = if (totalCount > 0) {
            (unlockedCount.toFloat() / totalCount.toFloat()).coerceIn(0f, 1f)
        } else {
            0f
        }
        return MilestoneCalculationResult(
            unlockedCount = unlockedCount,
            progressPercent = progress
        )
    }
}

data class MilestoneCalculationResult(
    val unlockedCount: Int,
    val progressPercent: Float
)
