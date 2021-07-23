package com.example.ivanov_p3

import android.app.Application
import com.example.ivanov_p3.di.*

//@HiltAndroidApp
class WallpapersApp : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
//            .appModule(AppModule(this))
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}