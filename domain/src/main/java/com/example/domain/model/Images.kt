package com.example.domain.model


data class Images(
    val id: Long,
    val urlFull: String?,
    val urlRegular: String?,
    val date: String?,
    val width: String?,
    val height: String?,
    val color: String?,
    val name: String?,
    val description: String?
)
