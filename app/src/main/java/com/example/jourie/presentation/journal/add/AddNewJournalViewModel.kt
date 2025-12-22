// File: .../presentation/journal/add/AddNewJournalViewModel.kt

package com.example.jourie.presentation.journal.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jourie.data.model.NewJournal
import com.example.jourie.data.repository.NewJournalRepository
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddNewJournalViewModel(
        private val repository: NewJournalRepository = NewJournalRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(AddNewJournalState())
    val state = _state.asStateFlow()

    init {
        setCurrentDate()
    }

    private fun setCurrentDate() {
        val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
        val dateString = dateFormat.format(Date())
        _state.update { it.copy(currentDate = dateString) }
    }

    fun onContentChange(newContent: String) {
        _state.update { it.copy(content = newContent) }
    }

    // --- DIPERBAIKI: Fungsi onSubmit sekarang menerima callback (String, String) -> Unit ---
    fun onSubmit(onJournalSubmitted: (String, String) -> Unit) {
        val content = state.value.content
        if (content.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val newJournal =
                        NewJournal(content = content, dateTimestamp = System.currentTimeMillis())
                // Simpan ke Firestore dan dapatkan ID jurnal yang baru dibuat
                val journalId = repository.insertJournal(newJournal)

                Log.d(
                        "AddNewJournalVM",
                        "Journal Submitted with id=$journalId, navigating to analysis."
                )

                // Berhenti loading dan lanjut ke layar analisis hanya jika simpan berhasil
                _state.update { it.copy(isLoading = false) }
                // KIRIM CONTENT DAN JOURNAL ID
                onJournalSubmitted(content, journalId)
            } catch (e: Exception) {
                Log.e("AddNewJournalVM", "Failed to submit journal", e)
                _state.update { it.copy(isLoading = false) }
                // Untuk sekarang kita hanya menghentikan loading; UI tidak akan crash.
                // Nanti bisa ditambah state.error untuk menampilkan pesan ke user.
            }
        }
    }
}
