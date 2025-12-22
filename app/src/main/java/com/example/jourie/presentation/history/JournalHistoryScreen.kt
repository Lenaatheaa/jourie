package com.example.jourie.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jourie.presentation.history.components.HistoryPageHeader
import com.example.jourie.presentation.history.components.JournalItemCard
import com.example.jourie.presentation.history.components.JournalSearchBar
import com.example.jourie.ui.theme.JourieTheme

// Layar utama dengan nama unik
@Composable
fun JournalHistoryScreen(
    navController: NavController,
    initialDateFilter: String? = null,
    viewModel: JournalHistoryViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    // Jika dipanggil dengan dateFilter dari Dashboard, gunakan untuk memfilter awal
    androidx.compose.runtime.LaunchedEffect(initialDateFilter) {
        if (!initialDateFilter.isNullOrBlank()) {
            viewModel.onSearchQueryChange(initialDateFilter)
        }
    }

    JourieTheme {
        Scaffold(
            topBar = {
                Column {
                    HistoryPageHeader()
                    Spacer(modifier = Modifier.height(12.dp))
                    JournalSearchBar(
                        query = state.searchQuery,
                        onQueryChange = viewModel::onSearchQueryChange
                    )
                }
            },
            // BottomBar akan ditangani oleh Navigasi Utama di MainActivity
        ) { innerPadding ->
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.filteredJournals.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.filteredJournals, key = { it.id }) { journal ->
                        JournalItemCard(
                            entry = journal,
                            onClick = {
                                val encodedContent = java.net.URLEncoder.encode(journal.description, "UTF-8")
                                navController.navigate("journal_analysis_screen/$encodedContent?journalId=${journal.id}")
                            },
                            onDeleteClick = {
                                viewModel.onDeleteJournal(journal.id)
                            }
                        )
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No journal entries found.")
                }
            }
        }
    }
}
