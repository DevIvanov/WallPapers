package com.example.data.repository

import com.example.data.database.ImagesDao
import com.example.data.database.ImagesEntity
import com.example.data.mapper.ImagesModelMapperImpl
import com.example.domain.model.Images
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImagesRepository @Inject constructor(
    val imagesDao: ImagesDao,
    val mapper: ImagesModelMapperImpl
) : Repository{


    override fun readAll(): Flow<List<Images>> {
        return imagesDao.readAllData().map { it.map(mapper::fromEntity) }
    }

    override suspend fun delete(image: Images) {
        return imagesDao.delete(mapper.toEntity(image))
    }

    override suspend fun insert(image: Images) {
        return imagesDao.insert(mapper.toEntity(image))
    }

    override suspend fun deleteAll() {
        imagesDao.deleteAll()
    }

    override suspend fun insertAll(images: List<Images>) {
        var imagesEntity: List<ImagesEntity> = images.map { mapper.toEntity(it) }
            imagesDao.insertAll(imagesEntity)
    }
}