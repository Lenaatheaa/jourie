package com.example.jourie.presentation.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jourie.data.firebase.FirebaseAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// State untuk menyimpan nilai-nilai di halaman edit profil
data class EditProfileState(
	val fullName: String = "",
	val phoneNumber: String = "",
	val email: String = "",
	val dob: String = "",
	val isUpdated: Boolean = false,
	val isLoading: Boolean = true,
	val error: String? = null
)

class EditProfileViewModel(
	private val repo: FirebaseAuthRepository = FirebaseAuthRepository()
) : ViewModel() {

	private val _state = MutableStateFlow(EditProfileState())
	val state = _state.asStateFlow()

	init {
		loadProfile()
	}

	private fun loadProfile() {
		viewModelScope.launch {
			_state.update { it.copy(isLoading = true, error = null) }
			val result = repo.getCurrentUserProfile()
			if (result.isSuccess) {
				val profile = result.getOrNull()!!
				_state.update {
					it.copy(
						isLoading = false,
						fullName = profile.fullName,
						email = profile.email,
						phoneNumber = profile.phone ?: "",
						dob = profile.dob ?: ""
					)
				}
			} else {
				_state.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.localizedMessage) }
			}
		}
	}

	// Fungsi untuk mengubah nilai saat pengguna mengetik
	fun onFullNameChange(newName: String) {
		_state.update { it.copy(fullName = newName) }
	}

	fun onPhoneNumberChange(newPhone: String) {
		_state.update { it.copy(phoneNumber = newPhone) }
	}

	fun onEmailChange(newEmail: String) {
		_state.update { it.copy(email = newEmail) }
	}

	fun onDobChange(newDob: String) {
		_state.update { it.copy(dob = newDob) }
	}

	// Fungsi yang dipanggil saat tombol "Update" ditekan
	fun onUpdateProfile() {
		viewModelScope.launch {
			val current = _state.value
			if (current.fullName.isBlank() || current.email.isBlank()) {
				_state.update { it.copy(error = "Nama dan email tidak boleh kosong.") }
				return@launch
			}
			_state.update { it.copy(isLoading = true, error = null) }
			val result = repo.updateCurrentUserProfile(
				fullName = current.fullName,
				phone = current.phoneNumber,
				email = current.email,
				dob = current.dob
			)
			if (result.isSuccess) {
				_state.update { it.copy(isLoading = false, isUpdated = true) }
			} else {
				_state.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.localizedMessage ?: "Gagal memperbarui profil.") }
			}
		}
	}

	// Fungsi untuk mereset state setelah navigasi kembali selesai
	fun onUpdateHandled() {
		_state.update { it.copy(isUpdated = false) }
	}
}
