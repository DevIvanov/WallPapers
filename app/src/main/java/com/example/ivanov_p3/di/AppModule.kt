package com.example.ivanov_p3.di

import android.content.Context
import com.example.data.database.HistoryDao
import com.example.data.database.HistoryDatabase
import com.example.data.database.ImagesDao
import com.example.data.database.ImagesDatabase
import com.example.data.mapper.HistoryModelMapperImpl
import com.example.data.mapper.ImagesModelMapperImpl
import com.example.data.network.api.UnsplashApi
import com.example.data.repository.HistoryRepository
import com.example.data.repository.ImagesRepository
import com.example.domain.repository.HRepository
import com.example.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi =
        retrofit.create(UnsplashApi::class.java)


    // Image database
    @Singleton
    @Provides
    fun getImagesDao(imagesDatabase: ImagesDatabase): ImagesDao {
        return imagesDatabase.imagesDao()
    }

    @Singleton
    @Provides
    fun getRepositoryImages(
        roomDatabase: ImagesDatabase,
        mapperImpl: ImagesModelMapperImpl
    ): Repository {
        return ImagesRepository(roomDatabase.imagesDao(), mapperImpl)
    }

    @Singleton
    @Provides
    fun getRoomDbInstanceImages(@ApplicationContext context: Context): ImagesDatabase {
        return ImagesDatabase.getDatabase(context)
    }


    // History Database
    @Singleton
    @Provides
    fun getHistoryDao(historyDatabase: HistoryDatabase): HistoryDao {
        return historyDatabase.historyDao()
    }

    @Singleton
    @Provides
    fun getRepositoryHistory(
        roomDatabase: HistoryDatabase,
        mapperImpl: HistoryModelMapperImpl
    ): HRepository {
        return HistoryRepository(roomDatabase.historyDao(), mapperImpl)
    }

    @Singleton
    @Provides
    fun getRoomDbInstanceHistory(@ApplicationContext context: Context): HistoryDatabase {
        return HistoryDatabase.getDatabase(context)
    }
}