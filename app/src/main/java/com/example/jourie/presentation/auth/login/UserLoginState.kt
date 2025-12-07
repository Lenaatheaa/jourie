package com.example.jourie.presentation.auth.login

// State dengan nama unik untuk layar Login
data class UserLoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,

    val error: String? = null
)
