package com.example.jourie.presentation.journal.analysis

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jourie.data.repository.NewJournalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel dengan nama unik
class JournalAnalysisViewModel(
        private val aiRepo: GeminiAiRepository = GeminiAiRepository(),
        private val journalRepo: NewJournalRepository = NewJournalRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(JournalAnalysisState())
    val state = _state.asStateFlow()

    // Simpan journalId untuk digunakan saat save dan load cache
    private var currentJournalId: String? = null

    /**
     * Dipanggil dari Composable setelah argumen navigasi (journalContent, journalId) sudah diambil
     * dari NavBackStackEntry. Dijaga agar hanya memicu analisis sekali untuk konten yang sama.
     */
    fun startAnalysis(content: String, journalId: String?) {
        this.currentJournalId = journalId // Simpan ID

        // Hindari memicu analisis berulang untuk konten yang sama
        if (_state.value.entryText.isNotEmpty()) return

        loadAnalysis(content)
    }

    private fun loadAnalysis(content: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, entryText = content) }

            // 1. STRATEGI CACHE-FIRST: Cek DB dulu!
            val journalId = currentJournalId
            var loadedFromDb = false

            if (journalId != null) {
                try {
                    Log.d(
                            "JournalAnalysisVM",
                            "Checking Firestore for existing analysis... ID: $journalId"
                    )
                    val existingAnalysis = journalRepo.getLatestAiAnalysis(journalId)

                    if (existingAnalysis != null) {
                        Log.d("JournalAnalysisVM", "Analysis found in DB! Loading from cache.")
                        val emotionScores = journalRepo.getEmotionScores(journalId)

                        // Mapping DB -> UI State
                        // Convert List<EmotionScoreData> back to Map<String, Float>
                        val emotionMap =
                                emotionScores.associate { it.emotionName to (it.score / 100f) }

                        _state.update {
                            it.copy(
                                    isLoading = false,
                                    predictionText = existingAnalysis.predictionSummary,
                                    rootCauseText = existingAnalysis.rootCause,
                                    recommendationText = existingAnalysis.recommendation,
                                    quoteText = existingAnalysis.quote,
                                    keywords =
                                            emptyList(), // Keywords sementara belum disimpan di DB
                                    emotionDistribution = emotionMap
                            )
                        }
                        loadedFromDb = true
                    } else {
                        Log.d("JournalAnalysisVM", "No analysis found in DB.")
                    }
                } catch (e: Exception) {
                    Log.e("JournalAnalysisVM", "Error checking DB, will fall back to API", e)
                }
            }

            // 2. Jika tidak ada di DB, panggil Gemini API
            if (!loadedFromDb) {
                try {
                    Log.d("JournalAnalysisVM", "Calling Gemini analyzeJournal()")
                    val result = aiRepo.analyzeJournal(content)

                    // Logging hasil...
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

                    Log.d("JournalAnalysisVM", "State updated from API result.")
                } catch (e: Exception) {
                    Log.e("JournalAnalysisVM", "Gemini analysis failed", e)
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
            }
        }
    }

    /** Menyimpan hasil analisis ke Firestore */
    fun saveAnalysis() {
        val journalId = currentJournalId
        if (journalId == null) {
            Log.e("JournalAnalysisVM", "Cannot save: Journal ID is null")
            return
        }

        viewModelScope.launch {
            try {
                // Konversi data state ke format repository
                val currentState = _state.value

                // 1. Save Main Analysis Data
                val dominantEmotion =
                        currentState.emotionDistribution.maxByOrNull { it.value }?.key ?: "Neutral"

                val analysisData =
                        NewJournalRepository.AiAnalysisData(
                                dominantEmotion = dominantEmotion,
                                predictionSummary = currentState.predictionText,
                                quote = currentState.quoteText,
                                recommendation = currentState.recommendationText,
                                rootCause = currentState.rootCauseText
                                // note: keywords belum disupport di repo, kita skip dulu atau
                                // tambah nanti
                                )

                // 2. Save Emotion Scores
                // Mapping simple colors based on emotion name (bisa diperbagus nanti)
                val emotionScores =
                        currentState.emotionDistribution.map { (emotion, value) ->
                            NewJournalRepository.EmotionScoreData(
                                    emotionName = emotion,
                                    score = (value * 100).toInt(), // Convert 0.4 -> 40
                                    colorHex = getColorForEmotion(emotion),
                                    comparisonTrend = 0 // Default 0 dulu
                            )
                        }

                journalRepo.saveAiAnalysis(journalId, analysisData)
                journalRepo.saveEmotionScores(journalId, emotionScores)

                Log.d("JournalAnalysisVM", "Analysis saved successfully for journalId: $journalId")
            } catch (e: Exception) {
                Log.e("JournalAnalysisVM", "Failed to save analysis", e)
            }
        }
    }

    private fun getColorForEmotion(emotion: String): String {
        return when (emotion.lowercase()) {
            "senang", "happy" -> "#FBBF24" // Yellow
            "tenang", "calm" -> "#A78BFA" // Purple
            "sedih", "sad" -> "#60A5FA" // Blue
            "cemas", "anxious" -> "#FB923C" // Orange
            "marah", "angry" -> "#F87171" // Red
            else -> "#CCCCCC" // Grey default
        }
    }
}
