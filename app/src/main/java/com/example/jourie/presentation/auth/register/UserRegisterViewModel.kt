// File: .../presentation/auth/register/UserRegisterViewModel.kt
package com.example.jourie.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jourie.data.firebase.FirebaseAuthRepository
import android.util.Patterns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// --- DIPERBAIKI: Mengimpor UserRegisterState dari filenya sendiri ---
import com.example.jourie.presentation.auth.register.UserRegisterState

// ---- TIDAK ADA LAGI DEFINISI DATA CLASS DI SINI ----

class UserRegisterViewModel : ViewModel() {
    // Baris ini sekarang tidak akan error karena UserRegisterState sudah diimpor
    private val _state = MutableStateFlow(UserRegisterState())
    val state = _state.asStateFlow()
    private val repo = FirebaseAuthRepository()

    fun onFullNameChange(name: String) {
        _state.update { it.copy(fullName = name) }
    }

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.update { it.copy(confirmPassword = confirmPassword) }
    }

    fun onPasswordVisibilityToggle() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onConfirmPasswordVisibilityToggle() {
        _state.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
    }

    fun register() {
        viewModelScope.launch {
            val fullName = _state.value.fullName
            val email = _state.value.email
            val password = _state.value.password
            val confirmPassword = _state.value.confirmPassword

            if (password != confirmPassword) {
                _state.update { it.copy(error = "Kata sandi tidak cocok.") }
                return@launch
            }
            if (fullName.isBlank() || email.isBlank() || password.isBlank()) {
                _state.update { it.copy(error = "Semua kolom harus diisi.") }
                return@launch
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _state.update { it.copy(error = "Format email tidak valid.") }
                return@launch
            }
            if (password.length < 6) {
                _state.update { it.copy(error = "Kata sandi minimal 6 karakter.") }
                return@launch
            }

            _state.update { it.copy(isLoading = true, error = null) }
            val result = repo.register(fullName, email, password)
            if (result.isSuccess) {
                _state.update { it.copy(isLoading = false, registerSuccess = true) }
            } else {
                _state.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.localizedMessage ?: "Registrasi gagal") }
            }
        }
    }
}
