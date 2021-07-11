package com.example.data.mapper

import com.example.data.database.ImagesEntity
import com.example.domain.mapper.ImagesModelMapper
import com.example.domain.model.Images

class ImagesModelMapperImpl : ImagesModelMapper<ImagesEntity, Images> {
    override fun fromEntity(from: ImagesEntity): Images {
        val images = Images(from.id, from.bitmap, from.link)
        return images
    }

    override fun toEntity(from: Images): ImagesEntity {
        val imagesEntity =
            ImagesEntity(from.id, from.bitmap, from.link)
        return imagesEntity
    }
}