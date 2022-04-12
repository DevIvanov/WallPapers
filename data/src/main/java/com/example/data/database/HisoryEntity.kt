package com.example.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val count: Int,
    val date: String,
    val favourite: Boolean
)