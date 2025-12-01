package com.example.jourie.data.repository

import com.example.jourie.data.model.NewJournal
import kotlinx.coroutines.delay

// Repository dengan nama unik untuk menyimpan jurnal baru
class NewJournalRepository {
    suspend fun insertJournal(journal: NewJournal) {
        // Simulasi menyimpan data ke database
        delay(1000)
        // Di aplikasi nyata, di sini Anda akan memanggil DAO dari Room Database
        println("Journal saved: ${journal.content}")
    }
}
