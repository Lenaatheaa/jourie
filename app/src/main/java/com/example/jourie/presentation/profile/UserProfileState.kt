// File: .../presentation/profile/UserProfileState.kt
package com.example.jourie.presentation.profile

// Ini adalah satu-satunya definisi data class di file ini
data class UserProfileState(
    val isLoading: Boolean = true,
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val dob: String = "",
    val isLoggedOut: Boolean = false,
    val showEditProfileDialog: Boolean = false // State untuk mengontrol dialog
)
