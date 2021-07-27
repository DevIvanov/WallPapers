package com.example.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ImagesDao {
    @Query("SELECT * FROM image_table ORDER BY id ASC")
    fun readAllData(): Flow<List<ImagesEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: ImagesEntity)

    @Delete
    fun delete(item: ImagesEntity)

    @Insert
    fun insertAll(items: List<ImagesEntity>)

    @Query("DELETE FROM image_table")
    suspend fun deleteAll()
}