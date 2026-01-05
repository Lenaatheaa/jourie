package com.example.jourie.presentation.streak

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.jourie.data.repository.StreakRepository
import com.example.jourie.domain.usecase.CalculateEvolutionProgressUseCase
import com.example.jourie.domain.usecase.GetStreakDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StreakViewModel(
    private val getStreakDataUseCase: GetStreakDataUseCase,
    private val calculateEvolutionProgressUseCase: CalculateEvolutionProgressUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(StreakState())
    val state = _state.asStateFlow()

    init {
        loadStreakData()
    }

    private fun loadStreakData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getStreakDataUseCase().onSuccess { data ->
                val progress = calculateEvolutionProgressUseCase(
                    daysForCurrentLevel = data.daysForCurrentLevel,
                    daysRequiredForNext = data.nextEvolution.daysRequired
                )
                _state.update {
                    it.copy(
                        isLoading = false,
                        streakData = data,
                        evolutionProgress = progress
                    )
                }
            }.onFailure { error ->
                _state.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }

    // Public function to refresh data when user changes
    fun refreshData() {
        loadStreakData()
    }
}
