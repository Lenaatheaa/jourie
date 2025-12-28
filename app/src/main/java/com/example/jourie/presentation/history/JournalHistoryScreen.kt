package com.example.jourie.presentation.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jourie.presentation.history.components.HistoryPageHeader
import com.example.jourie.presentation.history.components.JournalItemCard
import com.example.jourie.presentation.history.components.JournalSearchBar
import com.example.jourie.ui.theme.JourieTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun JournalHistoryScreen(
        navController: NavController,
        initialDateFilter: String? = null,
        viewModel: JournalHistoryViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    // Detect user change and refresh data
    val currentUserId = remember { FirebaseAuth.getInstance().currentUser?.uid }
    LaunchedEffect(currentUserId) {
        currentUserId?.let {
            viewModel.refreshData()
        }
    }

    // Jika dipanggil dengan dateFilter dari Dashboard, gunakan untuk memfilter awal
    androidx.compose.runtime.LaunchedEffect(initialDateFilter) {
        if (!initialDateFilter.isNullOrBlank()) {
            viewModel.onSearchQueryChange(initialDateFilter)
        }
    }

    JourieTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header dengan gradient card dan counter
            HistoryPageHeader(totalEntries = state.filteredJournals.size)

            Spacer(modifier = Modifier.height(16.dp))

            // Search bar
            JournalSearchBar(
                    query = state.searchQuery,
                    onQueryChange = viewModel::onSearchQueryChange
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Content area
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.filteredJournals.isNotEmpty()) {
                LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 120.dp, top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.filteredJournals, key = { it.id }) { journal ->
                        JournalItemCard(
                                entry = journal,
                                onClick = {
                                    val encodedContent =
                                            java.net.URLEncoder.encode(journal.description, "UTF-8")
                                    navController.navigate(
                                            "journal_analysis_screen/$encodedContent?journalId=${journal.id}"
                                    )
                                },
                                onDeleteClick = { viewModel.onDeleteJournal(journal.id) }
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
