// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/domain/usecase/GetStreakDataUseCase.kt

package com.example.jourie.domain.usecase

import StreakRepository
import com.example.jourie.data.model.StreakData
//import com.example.jourie.data.repository.StreakRepository

class GetStreakDataUseCase(
    private val repository: StreakRepository
) {
    suspend operator fun invoke(): Result<StreakData> {

        return runCatching {
            repository.getStreakData()
        }
    }
}
