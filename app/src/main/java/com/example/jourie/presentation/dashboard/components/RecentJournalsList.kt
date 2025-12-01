package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.data.model.JournalEntry
import com.example.jourie.presentation.history.components.JournalItemCard
import com.example.jourie.ui.theme.TextDark

@Composable
fun RecentJournalsList(journals: List<JournalEntry>) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Recent Journals",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
        journals.forEach { journal ->
            JournalItemCard(entry = journal) // Menggunakan kembali komponen dari layar History
        }
    }
}
