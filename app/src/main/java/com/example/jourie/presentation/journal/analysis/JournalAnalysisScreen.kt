// File:
// A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/journal/analysis/JournalAnalysisScreen.kt

package com.example.jourie.presentation.journal.analysis

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jourie.presentation.journal.analysis.components.*
import com.example.jourie.ui.theme.JourieTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalAnalysisScreen(
        navController: NavController,
        journalContent: String,
        journalId: String?,
        viewModel: JournalAnalysisViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    // Mulai analisis sekali setelah argumen diterima
    LaunchedEffect(journalContent, journalId) { viewModel.startAnalysis(journalContent, journalId) }

    Scaffold(
            topBar = {
                // Header ditempatkan di topBar agar posisinya tetap di atas
                AnalysisHeader(onBackClick = { navController.popBackStack() })
            }
    ) { innerPadding ->
        // Tampilkan error jika ada
        if (state.error != null) {
            Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
            ) {
                Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                            text = "❌ Analysis Failed",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                            text = state.error!!,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.popBackStack() }) { Text("Go Back") }
                }
            }
        } else if (state.isLoading) {
            Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("AI is analyzing your journal...")
                }
            }
        } else {
            // Tampilkan konten utama jika loading selesai
            LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Ringkasan Teks Jurnal
                item { EntrySummaryCard(entryText = state.entryText) }

                // 2. Spasi kecil dengan teks "AI Analysis"
                item {
                    Text(
                            text = "AI Analysis",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier =
                                    Modifier.fillMaxWidth()
                                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }

                // 3. Grafik Prediksi
                item {
                    PredictionChartSection(
                            emotionDistribution = state.emotionDistribution,
                            predictionText = state.predictionText
                    )
                }

                // 4. (Emotion meter removed)

                // 5. (Keyword section removed)

                // 6. Kartu-kartu Insight
                item {
                    InsightCards(
                            sentimentScore = state.sentimentScore,
                            rootCauseText = state.rootCauseText,
                            recommendationText = state.recommendationText
                    )
                }

                // 7. Kutipan
                item { QuoteSection(quoteText = state.quoteText) }

                // 8. Tombol-tombol Aksi
                item { AnalysisActionButtons(onShare = {}, onDownload = {}, onSave = {}) }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun JournalAnalysisScreenPreview() {
    val navController = rememberNavController()
    JourieTheme {
        JournalAnalysisScreen(
                navController = navController,
                journalContent = "Preview journal content",
                journalId = null
        )
    }
}
