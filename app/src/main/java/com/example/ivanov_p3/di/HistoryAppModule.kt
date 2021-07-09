//package com.example.ivanov_p3.di
//
//import android.app.Application
//import android.content.Context
//import com.example.data.database.HistoryDao
//import com.example.data.database.HistoryDatabase
//import com.example.data.mapper.HistoryModelMapperImpl
//import com.example.data.repository.HistoryRepository
//import com.example.data.repository.ImagesRepository
//import com.example.domain.interactor.HInteractor
//import com.example.domain.interactor.Interactor
//import com.example.domain.repository.HRepository
//import com.example.domain.repository.Repository
//import com.example.domain.use_cases.HUseCase
//import com.example.domain.use_cases.UseCase
//import dagger.Module
//import dagger.Provides
//import javax.inject.Singleton
//
//@Module
//class HistoryAppModule(val application: Application) {
//
//    @Singleton
//    @Provides
//    fun getHistoryDao(historyDatabase: HistoryDatabase): HistoryDao {
//        return historyDatabase.historyDao()
//    }
//
//    @Singleton
//    @Provides
//    fun getInteractor(): HInteractor {
//        return HInteractor(getUseCase())
//    }
//
//    @Singleton
//    @Provides
//    fun getUseCase(): HUseCase {
//        return HUseCase(getRepository())
//    }
//
//    @Singleton
//    @Provides
//    fun getRepository(): HRepository {
//        return HistoryRepository(getHistoryDao(getRoomDbInstance()), getMapper())
//    }
//
//    @Singleton
//    @Provides
//    fun getMapper(): HistoryModelMapperImpl {
//        return HistoryModelMapperImpl()
//    }
//
//    @Singleton
//    @Provides
//    fun getRoomDbInstance(): HistoryDatabase {
//        return HistoryDatabase.getDatabase(provideAppContext())
//    }
//
//    @Singleton
//    @Provides
//    fun provideAppContext(): Context {
//        return application.applicationContext
//    }
//}