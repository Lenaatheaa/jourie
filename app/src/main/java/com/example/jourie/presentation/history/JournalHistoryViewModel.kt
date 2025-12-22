package com.example.jourie.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jourie.data.repository.JournalRepository
import com.example.jourie.domain.usecase.GetFilteredJournalsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel dengan nama unik
class JournalHistoryViewModel(
    private val repository: JournalRepository = JournalRepository(),
    private val filterUseCase: GetFilteredJournalsUseCase = GetFilteredJournalsUseCase()
) : ViewModel() {

    private val _state = MutableStateFlow(JournalHistoryState())
    val state = _state.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val journals = repository.getAllJournalEntries()
                _state.update {
                    it.copy(
                        isLoading = false,
                        allJournals = journals,
                        filteredJournals = journals
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Failed to load history") }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
        val filtered = filterUseCase(query, _state.value.allJournals)
        _state.update { it.copy(filteredJournals = filtered) }
    }

    fun onDeleteJournal(journalId: String) {
        viewModelScope.launch {
            try {
                repository.deleteJournalEntry(journalId)

                _state.update { current ->
                    val updatedAll = current.allJournals.filterNot { it.id == journalId }
                    val updatedFiltered = current.filteredJournals.filterNot { it.id == journalId }
                    current.copy(
                        allJournals = updatedAll,
                        filteredJournals = updatedFiltered
                    )
                }
            } catch (e: Exception) {
                // Bisa ditambahkan error state spesifik jika diperlukan nanti
            }
        }
    }
}
