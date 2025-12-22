package com.example.jourie.presentation.journal.analysis

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel dengan nama unik
class JournalAnalysisViewModel(private val aiRepo: GeminiAiRepository = GeminiAiRepository()) :
        ViewModel() {

    private val _state = MutableStateFlow(JournalAnalysisState())
    val state = _state.asStateFlow()

    /**
     * Dipanggil dari Composable setelah argumen navigasi (journalContent, journalId) sudah diambil
     * dari NavBackStackEntry. Dijaga agar hanya memicu analisis sekali untuk konten yang sama.
     */
    fun startAnalysis(content: String, journalId: String?) {
        // Hindari memicu analisis berulang untuk konten yang sama
        if (_state.value.entryText.isNotEmpty()) return
        loadAnalysis(content)
    }

    private fun loadAnalysis(content: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, entryText = content) }

            try {
                Log.d("JournalAnalysisVM", "Calling Gemini analyzeJournal()")
                val result = aiRepo.analyzeJournal(content)

                Log.d("JournalAnalysisVM", "Received result from Gemini:")
                Log.d("JournalAnalysisVM", "  - recommendation: ${result.recommendation}")
                Log.d("JournalAnalysisVM", "  - prediction: ${result.prediction}")
                Log.d("JournalAnalysisVM", "  - rootCause: ${result.rootCause}")

                _state.update {
                    it.copy(
                            isLoading = false,
                            predictionText = result.prediction,
                            rootCauseText = result.rootCause,
                            recommendationText = result.recommendation,
                            quoteText = result.quote,
                            keywords = result.keywords,
                            emotionDistribution = result.emotionDistribution
                    )
                }

                Log.d(
                        "JournalAnalysisVM",
                        "State updated. recommendationText: ${_state.value.recommendationText}"
                )
            } catch (e: Exception) {
                Log.e("JournalAnalysisVM", "Gemini analysis failed", e)
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
