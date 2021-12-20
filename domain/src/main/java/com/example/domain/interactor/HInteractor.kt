package com.example.domain.interactor

import com.example.domain.model.History
import com.example.domain.use_cases.HUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HInteractor @Inject constructor(val useCase: HUseCase) {
    fun readAll(): Flow<List<History>> {
        return useCase.readAll()
    }

    suspend fun insert(history: History) {
        useCase.insert(history)
    }

    suspend fun delete(history: History) {
        useCase.delete(history)
    }

    suspend fun update(history: History) {
        useCase.update(history)
    }

    suspend fun deleteAll() {
        useCase.deleteAll()
    }
}