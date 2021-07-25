package com.example.data.mapper

import com.example.data.database.ImagesEntity
import com.example.domain.mapper.ImagesModelMapper
import com.example.domain.model.Images
import javax.inject.Inject

class ImagesModelMapperImpl @Inject constructor() : ImagesModelMapper<ImagesEntity, Images> {
    override fun fromEntity(from: ImagesEntity): Images {
        val images = Images(from.id, from.link, from.date, from.width, from.height, from.color, from.searchLink)
        return images
    }

    override fun toEntity(from: Images): ImagesEntity {
        val imagesEntity =
            ImagesEntity(from.id, from.link, from.date, from.width, from.height, from.color, from.searchLink)
        return imagesEntity
    }
}