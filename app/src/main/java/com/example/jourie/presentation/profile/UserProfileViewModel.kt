// File: .../presentation/profile/UserProfileViewModel.kt
package com.example.jourie.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jourie.data.firebase.FirebaseAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class UserProfileViewModel(
    private val repo: FirebaseAuthRepository = FirebaseAuthRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state = _state.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile(showLoading: Boolean = true) {
        viewModelScope.launch {
            if (showLoading) {
                _state.update { it.copy(isLoading = true) }
            }
            val result = repo.getCurrentUserProfile()
            if (result.isSuccess) {
                val profile = result.getOrNull()!!
                _state.update {
                    it.copy(
                        isLoading = false,
                        name = profile.fullName,
                        email = profile.email,
                        phone = profile.phone ?: "",
                        dob = profile.dob ?: ""
                    )
                }
            } else {
                // Jika gagal, tetap hentikan loading; bisa ditambah error state nanti
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    // Public function untuk refresh profile dari UI (silent refresh)
    fun refreshProfile() {
        loadProfile(showLoading = false)
    }

    // --- FUNGSI UNTUK DIALOG ---
    fun onShowEditDialog() {
        _state.update { it.copy(showEditProfileDialog = true) }
    }

    fun onDismissEditDialog() {
        _state.update { it.copy(showEditProfileDialog = false) }
    }

    // --- FUNGSI UNTUK MENYIMPAN PERUBAHAN ---
    fun onUpdateProfile(
        newName: String,
        newPhone: String,
        newEmail: String,
        newDob: String
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = repo.updateCurrentUserProfile(
                fullName = newName,
                phone = newPhone,
                email = newEmail,
                dob = newDob
            )
            if (result.isSuccess) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        name = newName,
                        phone = newPhone,
                        email = newEmail,
                        dob = newDob,
                        showEditProfileDialog = false
                    )
                }
            } else {
                // Gagal update: tutup loading, bisa tambahkan pesan error nanti
                _state.update { it.copy(isLoading = false, showEditProfileDialog = false) }
            }
        }
    }
    // ----------------------------

    // --- FUNGSI UNTUK LOGOUT ---
    fun logout() {
        viewModelScope.launch {
            repo.logout()
            _state.update { it.copy(isLoggedOut = true) }
        }
    }

    fun onLogoutHandled() {
        _state.update { it.copy(isLoggedOut = false) }
    }
}
