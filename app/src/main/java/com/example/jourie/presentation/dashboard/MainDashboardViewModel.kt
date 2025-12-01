package com.example.jourie.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jourie.data.repository.MainDashboardRepository
import com.example.jourie.domain.usecase.GetMainDashboardDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainDashboardViewModel(
    private val useCase: GetMainDashboardDataUseCase = GetMainDashboardDataUseCase(MainDashboardRepository())
) : ViewModel() {

    private val _state = MutableStateFlow(MainDashboardState())
    val state = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            useCase().onSuccess { newState ->
                _state.value = newState
            }.onFailure {
                _state.update { state -> state.copy(isLoading = false, error = "Failed to load dashboard data") }
            }
        }
    }
}
