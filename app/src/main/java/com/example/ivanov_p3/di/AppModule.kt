package com.example.ivanov_p3.di

import android.app.Application
import android.content.Context
import com.example.data.database.ImagesDao
import com.example.data.database.ImagesDatabase
import com.example.data.mapper.ImagesModelMapperImpl
import com.example.data.repository.ImagesRepository
import com.example.domain.interactor.Interactor
import com.example.domain.repository.Repository
import com.example.domain.use_cases.UseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {

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
}