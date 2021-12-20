package com.example.ivanov_p3.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.HInteractor
import com.example.domain.model.History
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(val interactor: HInteractor) : ViewModel() {

    val readAllData: LiveData<List<History>>

    init {
        readAllData = interactor.readAll().asLiveData()
    }

    fun addData(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.insert(history)
        }
    }

    fun deleteData(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.delete(history)
        }
    }

    fun updateData(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.update(history)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch {
            interactor.deleteAll()
        }
    }
}