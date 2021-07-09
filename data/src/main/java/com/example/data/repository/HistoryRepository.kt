package com.example.data.repository

import com.example.data.database.HistoryDao
import com.example.data.mapper.HistoryModelMapperImpl
import com.example.domain.model.History
import com.example.domain.repository.HRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    val historyDao: HistoryDao,
    val mapper: HistoryModelMapperImpl
) : HRepository {


    override fun readAll(): Flow<List<History>> {
        return historyDao.readAllData().map { it.map(mapper::fromEntity) }
    }

    override suspend fun insert(history: History) {
        return historyDao.insert(mapper.toEntity(history))
    }

    override suspend fun delete(history: History) {
        return historyDao.delete(mapper.toEntity(history))
    }

    override suspend fun update(history: History) {
        return historyDao.update(mapper.toEntity(history))
    }

    override suspend fun deleteAll() {
        historyDao.deleteAll()
    }



}