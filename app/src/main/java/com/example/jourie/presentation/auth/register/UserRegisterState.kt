package com.example.jourie.presentation.auth.register

// State dengan nama unik untuk layar Register
data class UserRegisterState(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val dob: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val registerSuccess: Boolean = false,
    val error: String? = null
)
