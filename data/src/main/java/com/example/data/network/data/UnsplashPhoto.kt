package com.example.data.network.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashPhoto(
    val id: String,
    val created_at: String?,
    val width: String?,
    val height: String?,
    val color: String?,
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashUser
    ) : Parcelable{

        @Parcelize
        data class UnsplashPhotoUrls(
            val raw: String,
            val full: String,
            val regular: String,
            val small: String,
            val thumb: String
        ) : Parcelable

        @Parcelize
        data class UnsplashUser(
            val name: String,
            val username: String
        ) : Parcelable {
            val attributionUrl get() = "https://unsplash.com/$username?utm_source=Ivanov_P3&utm_medium=referral"
        }
    }
