package com.example.jourie.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jourie.data.repository.UserProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel dengan nama unik
class UserProfileViewModel(
    private val repository: UserProfileRepository = UserProfileRepository() // Inisialisasi langsung
) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state = _state.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val profile = repository.getUserProfile()
                _state.update {
                    it.copy(
                        isLoading = false,
                        name = profile.name,
                        email = profile.email,
                        phone = profile.phone,
                        dob = profile.dateOfBirth
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Failed to load data") }
            }
        }
    }

    fun logout() {
        // Logika untuk logout bisa ditambahkan di sini
    }
}
