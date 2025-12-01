// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/domain/usecase/GetFilteredJournalsUseCase.kt

package com.example.jourie.domain.usecase

import com.example.jourie.data.model.JournalEntry

// UseCase dengan nama unik untuk memfilter jurnal
class GetFilteredJournalsUseCase {

    /**
     * Fungsi ini akan dipanggil untuk memfilter daftar jurnal.
     * @param searchQuery Teks yang diketik pengguna di search bar.
     * @param journals Daftar lengkap semua jurnal yang ada.
     * @return Daftar jurnal yang sudah difilter.
     */
    operator fun invoke(searchQuery: String, journals: List<JournalEntry>): List<JournalEntry> {
        // Jika query pencarian kosong, kembalikan semua jurnal tanpa filter.
        if (searchQuery.isBlank()) {
            return journals
        }

        // Bersihkan query dan ubah ke huruf kecil agar pencarian tidak case-sensitive.
        val query = searchQuery.trim().lowercase()

        // Lakukan filter berdasarkan beberapa kriteria:
        return journals.filter { entry ->
            entry.dateLabel.lowercase().contains(query) ||      // Cocokkan dengan label tanggal (e.g., "Today")
                    entry.description.lowercase().contains(query) ||    // Cocokkan dengan deskripsi
                    entry.mood.lowercase().contains(query) ||           // Cocokkan dengan mood
                    entry.tags.any { tag -> tag.lowercase().contains(query) } // Cocokkan dengan salah satu tag
        }
    }
}
