package com.example.ivanov_p3.ui.viewmodel

import androidx.lifecycle.*
import com.example.domain.interactor.HInteractor
import com.example.domain.model.History
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val interactor: HInteractor) : ViewModel() {


    private val _item = MutableLiveData<History?>().apply { value = null }
    val item: LiveData<History?> = _item

    val readAllData: LiveData<List<History>> = interactor.readAll().asLiveData()


    fun setItem(item: History) {
        _item.value = item
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