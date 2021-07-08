package com.example.ivanov_p3.di

import com.example.ivanov_p3.ui.ImagesViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(imagesViewModel: ImagesViewModel)
}