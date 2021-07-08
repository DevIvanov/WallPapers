package com.example.domain.interactor

import com.example.domain.model.Images
import com.example.domain.use_cases.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Interactor @Inject constructor(val useCase: UseCase) {
    fun readAll(): Flow<List<Images>> {
        return useCase.readAll()
    }

    suspend fun insert(image: Images) {
        useCase.insert(image)
    }

    suspend fun delete(image: Images) {
        useCase.delete(image)
    }

    suspend fun insertAll(images: List<Images>) {
        useCase.insertAll(images)
    }

    suspend fun deleteAll() {
        useCase.deleteAll()
    }
}