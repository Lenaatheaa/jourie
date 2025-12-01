package com.example.jourie.data.repository

import com.example.jourie.data.model.JournalEntry
import kotlinx.coroutines.delay

// Repository dengan nama unik untuk data dummy jurnal
class JournalRepository {
    suspend fun getAllJournalEntries(): List<JournalEntry> {
        delay(500) // Simulasi loading
        return listOf(
            JournalEntry(1, "Today", 24, "Nov", "Calm", "Had a wonderful morning meditation session. Felt incredibly calm and centered throughout the day. Grateful for the small moments of peace.", listOf("grateful", "peaceful")),
            JournalEntry(2, "Yesterday", 23, "Nov", "Stressed", "Work was challenging today. Felt overwhelmed by deadlines but managed to push through. Need to remember to take breaks more often.", listOf("overwhelmed", "challenging")),
            JournalEntry(3, "3 Days Ago", 22, "Nov", "Calm", "Had a wonderful morning meditation session. Felt incredibly calm and centered throughout the day. Grateful for the small moments of peace.", listOf("grateful", "peaceful")),
            JournalEntry(4, "Nov 21", 21, "Nov", "Happy", "Spent the evening with friends. It was a much-needed break and we had a lot of fun.", listOf("friends", "fun")),
            JournalEntry(5, "Nov 20", 20, "Nov", "Anxious", "Felt a bit anxious about the upcoming presentation. Prepared a lot to feel more confident.", listOf("work", "anxiety")),
            JournalEntry(6, "Nov 19", 19, "Nov", "Productive", "Finished a major project ahead of schedule. The feeling of accomplishment is amazing.", listOf("productive", "work"))
        )
    }
}
