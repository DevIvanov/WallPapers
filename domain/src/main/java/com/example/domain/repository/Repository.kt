package com.example.domain.repository

import com.example.domain.model.Images
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun readAll(): Flow<List<Images>>
    suspend fun delete(image: Images)
    suspend fun insert(image: Images)
    suspend fun deleteAll()
    suspend fun insertAll(images: List<Images>)
}