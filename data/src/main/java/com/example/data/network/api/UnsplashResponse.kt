package com.example.data.network.api

import com.example.data.network.data.UnsplashPhoto

data class UnsplashResponse(
    val total: String?,
    val results: List<UnsplashPhoto>
)