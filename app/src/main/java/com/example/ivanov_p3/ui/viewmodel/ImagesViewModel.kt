package com.example.ivanov_p3.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.Interactor
import com.example.domain.model.Images
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class ImagesViewModel @Inject constructor(val interactor: Interactor) : ViewModel() {

    val readAllData: LiveData<List<Images>>

    init {
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