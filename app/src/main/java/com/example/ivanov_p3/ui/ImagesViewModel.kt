package com.example.ivanov_p3.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.Interactor
import com.example.domain.model.Images
import com.example.ivanov_p3.WallpapersApp
import com.example.ivanov_p3.common.base.BaseViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


class ImagesViewModel(application: Application): BaseViewModel(application) {
    @Inject
    lateinit var interactor: Interactor
    val readAllData: LiveData<List<Images>>

    init {
        (application as WallpapersApp).getAppComponent().inject(this)
        readAllData = interactor.readAll().asLiveData()
    }

    fun addData(image: Images) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.insert(image)
        }
    }

    fun deleteData(image: Images) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.delete(image)
        }
    }

    private fun addAllData(images: List<Images>) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.insertAll(images)
        }
    }

    private fun deleteAllData() {
        viewModelScope.launch {
            interactor.deleteAll()
        }
    }

    @DelicateCoroutinesApi
    fun insertData(imagesList: List<Images>) {
        GlobalScope.async(Dispatchers.Main) {
            deleteAllData()
            delay(10L)
            addAllData(imagesList)
        }
    }

}