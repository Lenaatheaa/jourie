package com.example.jourie.presentation.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jourie.data.firebase.FirebaseAuthRepository
import com.example.jourie.data.repository.MilestonesRepository
import com.example.jourie.domain.usecase.GetUserMilestonesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel dengan nama unik
class MilestonesViewModel(
    private val repository: MilestonesRepository = MilestonesRepository(),
    private val useCase: GetUserMilestonesUseCase = GetUserMilestonesUseCase(),
    private val authRepository: FirebaseAuthRepository = FirebaseAuthRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(MilestonesState())
    val state = _state.asStateFlow()

    init {
        loadAchievements()
    }

    private fun loadAchievements() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val allBadges = repository.getAllBadges()
                val calculationResult = useCase(allBadges)

                // Fetch user profile for display name
                val profileResult = authRepository.getCurrentUserProfile()
                val fullName = profileResult.getOrNull()?.fullName ?: "User"

                _state.update {
                    it.copy(
                        isLoading = false,
                        userName = fullName,
                        streakBadges = allBadges.filter { badge -> badge.category == "Streak" },
                        journalBadges = allBadges.filter { badge -> badge.category == "Journal" },
                        badgeCount = calculationResult.unlockedCount,
                        progressPercent = calculationResult.progressPercent
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Failed to load achievements") }
            }
        }
    }

    // Public function to refresh data when user changes
    fun refreshData() {
        loadAchievements()
    }
}
