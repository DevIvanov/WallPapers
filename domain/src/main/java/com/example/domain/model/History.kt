package com.example.domain.model

data class History(
    val id: Long,
    val name: String = "",
    val count: Int,
    val date: String = "",
    val favourite: Boolean = false
)
