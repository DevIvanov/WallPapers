package com.example.domain.repository

import com.example.domain.model.History
import kotlinx.coroutines.flow.Flow

interface HRepository {
    fun readAll(): Flow<List<History>>
    suspend fun delete(history: History)
    suspend fun insert(history: History)
    suspend fun update(history: History)
    suspend fun deleteAll()

}