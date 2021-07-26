package com.example.ivanov_p3.api

import com.example.ivanov_p3.data.UnsplashPhoto

data class UnsplashResponse(
    val total: String?,
    val results: List<UnsplashPhoto>
)