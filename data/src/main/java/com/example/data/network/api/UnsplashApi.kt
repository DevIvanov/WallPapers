package com.example.data.network.api

import com.example.data.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    @Headers(HEADER_1, HEADER_2)
    @GET(GET)
    suspend fun searchPhotos(
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int,
        @Query(PER_PAGE) perPage: Int
    ): UnsplashResponse

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        private const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY

        const val HEADER_1 = "Accept-Version: v1"
        const val HEADER_2 = "Authorization: Client-ID $CLIENT_ID"
        const val GET = "search/photos"
        const val QUERY = "query"
        const val PAGE = "page"
        const val PER_PAGE = "per_page"
    }
}