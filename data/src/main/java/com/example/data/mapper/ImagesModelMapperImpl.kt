package com.example.data.mapper

import com.example.data.database.ImagesEntity
import com.example.domain.mapper.ImagesModelMapper
import com.example.domain.model.Images
import javax.inject.Inject

class ImagesModelMapperImpl @Inject constructor() : ImagesModelMapper<ImagesEntity, Images> {
    override fun fromEntity(from: ImagesEntity): Images {
        val images = Images(from.id, from.urlFull, from.urlRegular, from.date, from.width,
            from.height, from.color, from.name, from.description)
        return images
    }

    override fun toEntity(from: Images): ImagesEntity {
        val imagesEntity =
            ImagesEntity(from.id, from.urlFull, from.urlRegular, from.date, from.width,
                from.height, from.color, from.name, from.description)
        return imagesEntity
    }
}