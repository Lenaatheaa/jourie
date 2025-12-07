//// File: .../presentation/edit_profile/EditProfileViewModel.kt
//package com.example.jourie.presentation.edit_profile
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//
//// State untuk menyimpan nilai-nilai di halaman edit
//data class EditProfileState(
//    val fullName: String = "Alex",
//    val phoneNumber: String = "+123 567 89000",
//    val email: String = "alex@example.com",
//    val dob: String = "DD / MM / YYYY",
//    val isUpdated: Boolean = false
//)
//
//class EditProfileViewModel : ViewModel() {
//
//    private val _state = MutableStateFlow(EditProfileState())
//    val state = _state.asStateFlow()
//
//    // Fungsi untuk mengubah nilai saat pengguna mengetik
//    fun onFullNameChange(newName: String) {
//        _state.update { it.copy(fullName = newName) }
//    }
//
//    fun onPhoneNumberChange(newPhone: String) {
//        _state.update { it.copy(phoneNumber = newPhone) }
//    }
//
//    fun onEmailChange(newEmail: String) {
//        _state.update { it.copy(email = newEmail) }
//    }
//
//    fun onDobChange(newDob: String) {
//        _state.update { it.copy(dob = newDob) }
//    }
//
//    // Fungsi yang dipanggil saat tombol "Update" ditekan
//    fun onUpdateProfile() {
//        viewModelScope.launch {
//            // Di sini Anda akan menambahkan logika untuk menyimpan data ke database/API
//            println("Updating profile...")
//            // Untuk simulasi, kita ubah state isUpdated menjadi true untuk memicu navigasi kembali
//            _state.update { it.copy(isUpdated = true) }
//        }
//    }
//
//    // Fungsi untuk mereset state setelah navigasi kembali selesai
//    fun onUpdateHandled() {
//        _state.update { it.copy(isUpdated = false) }
//    }
//}
