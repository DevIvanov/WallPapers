package com.example.domain.use_cases

import com.example.domain.model.Images
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UseCase @Inject constructor(private val repository: Repository) {
    fun readAll(): Flow<List<Images>> {
        return repository.readAll()
    }

    suspend fun insert (image: Images) {
        repository.insert(image)
    }

    suspend fun delete(image: Images) {
        repository.delete(image)
    }

    suspend fun insertAll(images: List<Images>) {
        repository.insertAll(images)
    }

    suspend fun deleteAll() {
        repository.deleteAll()
    }
}