package com.example.data.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images_table")
data class ImagesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val bitmap: String?
)
