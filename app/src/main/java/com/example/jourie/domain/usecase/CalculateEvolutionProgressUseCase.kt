// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/domain/usecase/CalculateEvolutionProgressUseCase.kt

package com.example.jourie.domain.usecase

// KELAS INI HANYA UNTUK MENGHITUNG PROGRES, TIDAK ADA HUBUNGANNYA DENGAN JURNAL

class CalculateEvolutionProgressUseCase {
    /**
     * Menghitung progres evolusi sebagai nilai Float antara 0.0 dan 1.0.
     * @param daysForCurrentLevel Jumlah hari yang sudah diselesaikan pada level saat ini.
     * @param daysRequiredForNext Total hari yang dibutuhkan untuk naik ke level berikutnya.
     * @return Nilai Float progres, contoh: 0.75f untuk 75%.
     */
    operator fun invoke(daysForCurrentLevel: Int, daysRequiredForNext: Int): Float {
        // Hindari pembagian dengan nol jika data tidak valid
        if (daysRequiredForNext <= 0) {
            return 0f
        }

        // Hitung progres dan pastikan nilainya tidak lebih dari 1.0
        val progress = daysForCurrentLevel.toFloat() / daysRequiredForNext.toFloat()
        return progress.coerceIn(0f, 1f)
    }
}
