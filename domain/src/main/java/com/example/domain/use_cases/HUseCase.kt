package com.example.domain.use_cases

import com.example.domain.model.History
import com.example.domain.repository.HRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class HUseCase @Inject constructor(private val repository: HRepository) {
    fun readAll(): Flow<List<History>> {
        return repository.readAll()
    }

    suspend fun insert (history: History) {
        repository.insert(history)
    }

    suspend fun delete(history: History) {
        repository.delete(history)
    }

    suspend fun update(history: History) {
        repository.update(history)
    }

    suspend fun deleteAll() {
        repository.deleteAll()
    }
}