package com.example.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ImagesDao {
    @Query("SELECT * FROM images_table ORDER BY id ASC")
    fun readAllData(): Flow<List<ImagesEntity>>

    @Insert
    fun insert(item: ImagesEntity)

    @Delete
    fun delete(item: ImagesEntity)

    @Insert
    fun insertAll(items: List<ImagesEntity>)

    @Query("DELETE FROM images_table")
    suspend fun deleteAll()
}