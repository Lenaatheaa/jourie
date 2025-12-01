package com.example.jourie.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.jourie.presentation.history.components.HistoryPageHeader
import com.example.jourie.presentation.history.components.JournalItemCard
import com.example.jourie.presentation.history.components.JournalSearchBar
import com.example.jourie.ui.theme.JourieTheme

// Layar utama dengan nama unik
@Composable
fun JournalHistoryScreen(
    viewModel: JournalHistoryViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    JourieTheme {
        Scaffold(
            topBar = {
                Column {
                    HistoryPageHeader()
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
                        JournalItemCard(entry = journal)
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
