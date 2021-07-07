package com.example.domain.mapper

interface ImagesModelMapper<E, M> {
    fun fromEntity(from: E): M
    fun toEntity(from: M): E
}