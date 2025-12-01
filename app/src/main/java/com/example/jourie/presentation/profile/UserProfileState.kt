package com.example.jourie.presentation.profile

// State dengan nama unik
data class UserProfileState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val dob: String = "",
    val isLoading: Boolean = true,
    val error: String? = null
)
