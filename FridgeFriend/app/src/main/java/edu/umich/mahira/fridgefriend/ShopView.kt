package edu.umich.mahira.fridgefriend

import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ShopView(private val repository: ShopRepository) : ViewModel() {
    // instead of viewmodel use savedstatemodel? so items wont be deleted when more storage is needed?
    // https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allItems: LiveData<List<Shop>> = repository.allItems.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(item: Shop) = viewModelScope.launch {
        repository.insert(item)
    }
    // add in edit and delete functions
}

class ShopViewFactory(private val repository: ShopRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopView::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShopView(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}