package com.example.jourie.data.model

// Menggunakan nama UserProfile untuk menghindari konflik
data class UserProfile(
    val name: String,
    val email: String,
    val phone: String,
    val dateOfBirth: String
)