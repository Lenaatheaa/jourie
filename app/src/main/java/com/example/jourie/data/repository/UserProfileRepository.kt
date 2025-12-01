package com.example.jourie.data.repository

import com.example.jourie.data.model.UserProfile
import kotlinx.coroutines.delay

// Repository dengan nama unik untuk data dummy profil
class UserProfileRepository {
    suspend fun getUserProfile(): UserProfile {
        delay(500) // Simulasi loading
        return UserProfile(
            name = "Alex",
            email = "alex.johnson@email.com",
            phone = "+1 (555) 123-4567",
            dateOfBirth = "March 15, 1995"
        )
    }
}
