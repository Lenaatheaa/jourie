package com.example.jourie.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun register(fullName: String, email: String, password: String): Result<Unit> {
        return try {
            val userCredential = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = userCredential.user?.uid ?: throw IllegalStateException("No UID after register")

            val data = mapOf(
                "fullName" to fullName,
                "email" to email,
                "createdAt" to System.currentTimeMillis()
            )
            firestore.collection("users").document(uid).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(mapFriendly(e))
        }
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(mapFriendly(e))
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun currentUserId(): String? = auth.currentUser?.uid

    data class UserProfileData(
        val fullName: String = "",
        val email: String = "",
        val phone: String? = null,
        val dob: String? = null
    )

    suspend fun getCurrentUserProfile(): Result<UserProfileData> {
        return try {
            val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("User belum login"))
            val snapshot = firestore.collection("users").document(uid).get().await()
            if (!snapshot.exists()) {
                return Result.failure(IllegalStateException("Profil pengguna tidak ditemukan"))
            }
            val fullName = snapshot.getString("fullName") ?: ""
            val email = snapshot.getString("email") ?: ""
            val phone = snapshot.getString("phone")
            val dob = snapshot.getString("dob")
            Result.success(UserProfileData(fullName, email, phone, dob))
        } catch (e: Exception) {
            Result.failure(mapFriendly(e))
        }
    }

    suspend fun updateCurrentUserProfile(
        fullName: String,
        phone: String?,
        email: String,
        dob: String?
    ): Result<Unit> {
        return try {
            val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("User belum login"))
            val data = mapOf(
                "fullName" to fullName,
                "email" to email,
                "phone" to (phone ?: ""),
                "dob" to (dob ?: "")
            )
            firestore.collection("users").document(uid)
                .set(data, SetOptions.merge())
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(mapFriendly(e))
        }
    }

    private fun mapFriendly(e: Exception): Exception {
        val message = when (e) {
            is FirebaseAuthException -> when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> "Format email tidak valid."
                "ERROR_WRONG_PASSWORD" -> "Kata sandi salah."
                "ERROR_USER_NOT_FOUND" -> "Akun tidak ditemukan."
                "ERROR_USER_DISABLED" -> "Akun dinonaktifkan."
                "ERROR_EMAIL_ALREADY_IN_USE" -> "Email sudah digunakan."
                "ERROR_WEAK_PASSWORD" -> "Kata sandi terlalu lemah. Gunakan kombinasi huruf, angka, dan simbol."
                else -> e.localizedMessage ?: "Terjadi kesalahan autentikasi."
            }
            is FirebaseAuthInvalidCredentialsException -> "Email atau kata sandi tidak valid."
            is java.net.UnknownHostException -> "Tidak ada koneksi internet. Periksa jaringan Anda."
            is com.google.firebase.FirebaseNetworkException -> "Gagal terhubung ke server. Coba lagi nanti."
            else -> e.localizedMessage ?: "Terjadi kesalahan. Coba lagi."
        }
        return RuntimeException(message, e)
    }
}
