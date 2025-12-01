package com.example.jourie.presentation.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel dengan nama unik
class UserRegisterViewModel : ViewModel() {

    private val _state = MutableStateFlow(UserRegisterState())
    val state = _state.asStateFlow()

    fun onFullNameChange(name: String) {
        _state.update { it.copy(fullName = name) }
    }

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun onConfirmPasswordChange(password: String) {
        _state.update { it.copy(confirmPassword = password) }
    }

    fun onPasswordVisibilityToggle() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onConfirmPasswordVisibilityToggle() {
        _state.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
    }

    fun register(onRegisterSuccess: () -> Unit) {
        if (state.value.password != state.value.confirmPassword) {
            _state.update { it.copy(error = "Kata sandi tidak cocok") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            delay(1500) // Simulasi proses registrasi
            Log.d("UserRegister", "Register Success for user: ${state.value.email}")
            _state.update { it.copy(isLoading = false) }
            onRegisterSuccess() // Panggil callback navigasi
        }
    }
}
