package com.example.jourie.presentation.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel dengan nama unik
class UserLoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(UserLoginState())
    val state = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun onPasswordVisibilityToggle() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun login(onLoginSuccess: () -> Unit) {
        if (state.value.email.isBlank()) {
            _state.update { it.copy(error = "Email tidak boleh kosong") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            delay(1000) // Simulasi proses login
            Log.d("UserLogin", "Login Success for user: ${state.value.email}")
            _state.update { it.copy(isLoading = false) }
            onLoginSuccess() // Panggil callback navigasi
        }
    }
}
