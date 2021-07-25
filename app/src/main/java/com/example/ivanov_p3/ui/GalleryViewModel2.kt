//package com.example.ivanov_p3.ui
//
//import androidx.hilt.Assisted
//import androidx.hilt.lifecycle.ViewModelInject
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.switchMap
//import androidx.lifecycle.viewModelScope
//import androidx.paging.cachedIn
//import com.example.ivanov_p3.data.UnsplashRepository
//import javax.inject.Inject
//
//
//class GalleryViewModel @ViewModelInject constructor(
//    private val repository: UnsplashRepository,
//    @Assisted state: SavedStateHandle
//) : ViewModel() {
//    @Inject
//
//    @JvmField
//    val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
//
//    val photos = currentQuery.switchMap { queryString ->
//        repository.getSearchResults(queryString).cachedIn(viewModelScope)
//    }
//
//    fun searchPhotos(query: String) {
//        currentQuery.value = query
//    }
//
//    companion object {
//        private const val CURRENT_QUERY = "current_query"
//        private const val DEFAULT_QUERY = "cats"
//    }
//}