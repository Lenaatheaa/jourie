// File: .../presentation/profile/UserProfileViewModel.kt
package com.example.jourie.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class UserProfileViewModel : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state = _state.asStateFlow()

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
            // Di sini Anda akan menambahkan logika untuk menyimpan data ke database/API
            println("Updating profile with new data...")
            _state.update {
                it.copy(
                    name = newName,
                    phone = newPhone,
                    email = newEmail,
                    dob = newDob,
                    showEditProfileDialog = false // Langsung tutup dialog setelah update
                )
            }
        }
    }
    // ----------------------------

    // --- FUNGSI UNTUK LOGOUT ---
    fun logout() {
        viewModelScope.launch {
            _state.update { it.copy(isLoggedOut = true) }
        }
    }

    fun onLogoutHandled() {
        _state.update { it.copy(isLoggedOut = false) }
    }
}
