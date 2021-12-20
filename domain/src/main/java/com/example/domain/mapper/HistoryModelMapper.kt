package com.example.domain.mapper

interface HistoryModelMapper<E, M> {
    fun fromEntity(from: E): M
    fun toEntity(from: M): E
}