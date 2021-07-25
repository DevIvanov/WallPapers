package com.example.ivanov_p3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory constructor(val imagesViewModel: ImagesViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if(modelClass == ImagesViewModel::class.java){
            imagesViewModel as T
        }else{
            throw  IllegalStateException("Unknown entity")
        }
}