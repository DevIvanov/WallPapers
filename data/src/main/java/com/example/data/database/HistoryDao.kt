package com.example.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {
    @Query("SELECT * FROM history_table ORDER BY id ASC")
    fun readAllData(): Flow<List<HistoryEntity>>

    @Insert
    fun insert(item: HistoryEntity)

    @Update
    fun update(item: HistoryEntity)

    @Delete
    fun delete(item: HistoryEntity)

    @Query("DELETE FROM history_table")
    suspend fun deleteAll()
}