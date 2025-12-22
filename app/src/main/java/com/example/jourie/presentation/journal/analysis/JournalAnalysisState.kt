package com.example.jourie.presentation.journal.analysis

// State dengan nama unik untuk layar analisis
data class JournalAnalysisState(
    val isLoading: Boolean = true,
    val entryText: String = "",
    val moodScore: Int = 0,
    val sentimentScore: Int = 0,
    val predictionText: String = "",
    val rootCauseText: String = "",
    val recommendationText: String = "",
    val quoteText: String = "",
    val keywords: List<String> = emptyList(),
    val emotionDistribution: Map<String, Float> = emptyMap(),
    val error: String? = null
)
