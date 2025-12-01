// File: .../presentation/journal/add/AddNewJournalViewModel.kt

package com.example.jourie.presentation.journal.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jourie.data.model.NewJournal
import com.example.jourie.data.repository.NewJournalRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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

    // --- DIPERBAIKI: Fungsi onSubmit sekarang menerima callback ---
    fun onSubmit(onJournalSubmitted: (String) -> Unit) {
        val content = state.value.content
        if (content.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val newJournal = NewJournal(
                content = content,
                dateTimestamp = System.currentTimeMillis()
            )
            repository.insertJournal(newJournal)

            Log.d("AddNewJournalVM", "Journal Submitted, navigating to analysis.")
            _state.update { it.copy(isLoading = false) }

            // Panggil callback navigasi dengan mengirim konten jurnal
            onJournalSubmitted(content)
        }
    }
}
