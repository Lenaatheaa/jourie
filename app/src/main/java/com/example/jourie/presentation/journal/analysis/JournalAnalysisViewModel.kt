package com.example.jourie.presentation.journal.analysis

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel dengan nama unik
class JournalAnalysisViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(JournalAnalysisState())
    val state = _state.asStateFlow()

    // Ambil konten jurnal dari argumen navigasi
    private val journalContent: String = savedStateHandle.get<String>("journalContent") ?: "No content provided."

    init {
        loadAnalysis(journalContent)
    }

    private fun loadAnalysis(content: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, entryText = content) }

            // Simulasi proses analisis AI yang memakan waktu
            delay(2000)

            // Generate data dummy setelah loading selesai
            _state.update {
                it.copy(
                    isLoading = false,
                    moodScore = 8,
                    sentimentScore = 85,
                    keywords = listOf("grateful", "peaceful", "centered"),
                    emotionDistribution = mapOf(
                        "Happy" to 0.20f,
                        "Sad" to 0.15f,
                        "Anxious" to 0.12f,
                        "Calm" to 0.22f,
                        "Angry" to 0.23f,
                        "Peaceful" to 0.08f
                    )
                )
            }
        }
    }
}
