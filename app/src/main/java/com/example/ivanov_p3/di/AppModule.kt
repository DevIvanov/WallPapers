package com.example.ivanov_p3.di

import android.app.Application
import android.content.Context
import com.example.data.database.HistoryDao
import com.example.data.database.HistoryDatabase
import com.example.data.database.ImagesDao
import com.example.data.database.ImagesDatabase
import com.example.data.mapper.HistoryModelMapperImpl
import com.example.data.mapper.ImagesModelMapperImpl
import com.example.data.repository.HistoryRepository
import com.example.data.repository.ImagesRepository
import com.example.domain.interactor.HInteractor
import com.example.domain.interactor.Interactor
import com.example.domain.repository.HRepository
import com.example.domain.repository.Repository
import com.example.domain.use_cases.HUseCase
import com.example.domain.use_cases.UseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun getImagesDao(imagesDatabase: ImagesDatabase): ImagesDao {
        return imagesDatabase.imagesDao()
    }

    @Singleton
    @Provides
    fun getInteractor(): Interactor {
        return Interactor(getUseCase())
    }

    @Singleton
    @Provides
    fun getUseCase(): UseCase {
        return UseCase(getRepository())
    }

    @Singleton
    @Provides
    fun getRepository(): Repository {
        return ImagesRepository(getImagesDao(getRoomDbInstance()), getMapper())
    }

    @Singleton
    @Provides
    fun getMapper(): ImagesModelMapperImpl {
        return ImagesModelMapperImpl()
    }

    @Singleton
    @Provides
    fun getRoomDbInstance(): ImagesDatabase {
        return ImagesDatabase.getDatabase(provideAppContext())
    }

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }

    // AppModule for HistoryDatabase
    @Singleton
    @Provides
    fun getHistoryDao(historyDatabase: HistoryDatabase): HistoryDao {
        return historyDatabase.historyDao()
    }

    @Singleton
    @Provides
    fun getHistoryInteractor(): HInteractor {
        return HInteractor(getHistoryUseCase())
    }

    @Singleton
    @Provides
    fun getHistoryUseCase(): HUseCase {
        return HUseCase(getHistoryRepository())
    }

    @Singleton
    @Provides
    fun getHistoryRepository(): HRepository {
        return HistoryRepository(getHistoryDao(getHistoryRoomDbInstance()), getHistoryMapper())
    }

    @Singleton
    @Provides
    fun getHistoryMapper(): HistoryModelMapperImpl {
        return HistoryModelMapperImpl()
    }

    @Singleton
    @Provides
    fun getHistoryRoomDbInstance(): HistoryDatabase {
        return HistoryDatabase.getDatabase(provideHistoryAppContext())
    }

    @Singleton
    @Provides
    fun provideHistoryAppContext(): Context {
        return application.applicationContext
    }

}