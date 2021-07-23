//package com.example.ivanov_p3.ui
//
////import androidx.hilt.Assisted
////import androidx.hilt.lifecycle.ViewModelInject
//import android.app.Application
//import androidx.lifecycle.*
//import androidx.paging.cachedIn
//import com.example.ivanov_p3.WallpapersApp
//import com.example.ivanov_p3.common.base.BaseViewModel
//import com.example.ivanov_p3.data.UnsplashRepository
////import dagger.assisted.Assisted
//import dagger.assisted.AssistedInject
//import javax.inject.Inject
//
////class GalleryViewModel constructor(
//    class GalleryViewModel @ViewModelInject constructor(
//    private val repository: UnsplashRepository,
//    @Assisted state: SavedStateHandle
//) : ViewModel() {
//    @Inject
//
//    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
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