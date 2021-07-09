package com.example.ivanov_p3.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.HInteractor
import com.example.domain.model.History
import com.example.ivanov_p3.WallpapersApp
import kotlinx.coroutines.*
import javax.inject.Inject


class HistoryViewModel(application: Application): AndroidViewModel(application) {
    @Inject
    lateinit var interactor: HInteractor
    val readAllData: LiveData<List<History>>

    init {
        (application as WallpapersApp).getAppComponent().inject(this)
        readAllData = interactor.readAll().asLiveData()
    }

    fun addData(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.insert(history)
        }
    }

    fun deleteData(history: History) {
        viewModelScope.launch {
            interactor.delete(history)
        }
    }

    private fun updateData(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.update(history)
        }
    }

    private fun deleteAllData() {
        viewModelScope.launch {
            interactor.deleteAll()
        }
    }
}