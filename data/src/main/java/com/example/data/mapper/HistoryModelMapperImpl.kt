package com.example.data.mapper

import com.example.data.database.HistoryEntity
import com.example.domain.mapper.HistoryModelMapper
import com.example.domain.model.History
import javax.inject.Inject

class HistoryModelMapperImpl @Inject constructor()  : HistoryModelMapper<HistoryEntity, History> {
    override fun fromEntity(from: HistoryEntity): History {
        return History(from.id, from.name, from.count, from.date, from.favourite)
    }

    override fun toEntity(from: History): HistoryEntity {
        return HistoryEntity(from.id, from.name, from.count, from.date, from.favourite)
    }
}