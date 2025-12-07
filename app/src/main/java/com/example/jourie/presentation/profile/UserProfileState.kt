// File: .../presentation/profile/UserProfileState.kt
package com.example.jourie.presentation.profile

// Ini adalah satu-satunya definisi data class di file ini
data class UserProfileState(
    val isLoading: Boolean = false,
    val name: String = "Jessica",
    val email: String = "jessica@email.com",
    val phone: String = "+62 123 456 7890",
    val dob: String = "01 January 1995",
    val isLoggedOut: Boolean = false,
    val showEditProfileDialog: Boolean = false // State untuk mengontrol dialog
)
