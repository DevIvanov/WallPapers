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
class ImagesViewModel @Inject constructor(private val interactor: Interactor) : ViewModel() {

    val readAllData: LiveData<List<Images>> = interactor.readAll().asLiveData()

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
}