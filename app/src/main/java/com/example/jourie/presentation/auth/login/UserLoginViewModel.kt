// File: .../presentation/auth/login/UserLoginViewModel.kt
package com.example.jourie.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jourie.data.firebase.FirebaseAuthRepository
import android.util.Patterns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Mengimpor UserLoginState dari filenya sendiri
import com.example.jourie.presentation.auth.login.UserLoginState

class UserLoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(UserLoginState())
    val state = _state.asStateFlow()
    private val repo = FirebaseAuthRepository()

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun onPasswordVisibilityToggle() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun login() {
        if (_state.value.isLoading) return
        viewModelScope.launch {
            val email = _state.value.email
            val password = _state.value.password
            if (email.isBlank() || password.isBlank()) {
                _state.update { it.copy(error = "Email dan kata sandi tidak boleh kosong.") }
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
            val result = repo.login(email, password)
            if (result.isSuccess) {
                _state.update { it.copy(isLoading = false, loginSuccess = true) }
            } else {
                _state.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.localizedMessage ?: "Login gagal") }
            }
        }
    }
}
