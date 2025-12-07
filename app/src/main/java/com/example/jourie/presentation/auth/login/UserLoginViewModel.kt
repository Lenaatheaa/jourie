//// File: .../presentation/auth/login/UserLoginViewModel.kt
//package com.example.jourie.presentation.auth.login
//
//import androidx.compose.animation.core.copy
//import androidx.compose.ui.semantics.password
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
////import kotlinx.coroutines.flow.MutableStateFlowimport
//import kotlinx.coroutines.flow.asStateFlow
//
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//
//// --- DIPERBAIKI: Mengimpor UserLoginState dari filenya sendiri ---
//import com.example.jourie.presentation.auth.login.UserLoginState
//
//// ---- TIDAK ADA LAGI DEFINISI DATA CLASS DI SINI ----
//
//class UserLoginViewModel : ViewModel() {
//    // Baris ini sekarang tidak akan error karena UserLoginState sudah diimpor
//    private val _state = MutableStateFlow(UserLoginState())
//    val state = _state.asStateFlow()
//
//    fun onEmailChange(email: String) {
//        _state.update { it.copy(email = email) }
//    }
//
//    fun onPasswordChange(password: String) {
//        _state.update { it.copy(password = password) }
//    }
//
//    fun onPasswordVisibilityToggle() {
//        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
//    }
//
//    fun login() {
//        viewModelScope.launch {
//            // Logika validasi dan login Anda akan ada di sini
//            _state.update { it.copy(isLoading = true, error = null) }
//            // Simulasi login
//            kotlinx.coroutines.delay(1500)
//
//            if (_state.value.email.isNotBlank() && _state.value.password.isNotBlank()) {
//                _state.update { it.copy(isLoading = false, loginSuccess = true) }
//            } else {
//                _state.update { it.copy(isLoading = false, error = "Email dan kata sandi tidak boleh kosong.") }
//            }
//        }
//    }
//}

// File: .../presentation/auth/login/UserLoginViewModel.kt
package com.example.jourie.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Mengimpor UserLoginState dari filenya sendiri
import com.example.jourie.presentation.auth.login.UserLoginState

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

    // --- FUNGSI LOGIN YANG DIPERBAIKI ---
    fun login() {
        // Jangan jalankan jika sudah loading
        if (_state.value.isLoading) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            // Simulasi proses login di server
            kotlinx.coroutines.delay(1500)

            // Logika validasi sederhana
            if (_state.value.email.isNotBlank() && _state.value.password.isNotBlank()) {
                // --- INI BAGIAN PENTING YANG HILANG ---
                // Beri tahu UI bahwa login berhasil!
                _state.update { it.copy(isLoading = false, loginSuccess = true) }
            } else {
                // Jika validasi gagal, tampilkan error
                _state.update { it.copy(isLoading = false, error = "Email dan kata sandi tidak boleh kosong.") }
            }
        }
    }
}
