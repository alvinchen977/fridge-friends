package edu.umich.mahira.fridgefriend

import androidx.lifecycle.*
import kotlinx.coroutines.launch


// Allows us to keep a reference to our repository and a list of all items.
class ShopView(private val repository: ShopRepository) : ViewModel() {
    // instead of viewmodel use savedstatemodel? so items wont be deleted when more storage is needed?
    // https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate

    /*
    Using LiveData and caching what allItems returns allows us to put an observer on the data
    (instead of polling for changes) and only update the UI when the data actually changes.
    note: repository is completely separated from the UI through the ViewModel()
     */
    //val allItems: LiveData<List<Array<String>> = repository.allItems.asLiveData()
    val allItems: LiveData<List<Shop>> = repository.allItems.asLiveData()
    //val changingItem: LiveData<String>.asLiveData()

    //val isEmpty: Boolean = (allItems.value == null)

    // Launching a new coroutine to insert the data in a non-blocking way
    fun insert(shop: Shop) = viewModelScope.launch {
        repository.insert(shop)
    }
    /*fun insert(item: Shop) = viewModelScope.launch {
        repository.insert(item)
    }*/

    fun delete(item: Shop) = viewModelScope.launch {
        repository.delete(item)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

//    fun isEmpty(): Boolean {
//        return (allItems.value == null)
//    }

    // fix to update with list of user categories set to asLiveData
    /*fun determineCategory(category: String) : Int {
        var c = 0
        if (category == "Produce") {
            c = 1
        }
        return c
    }*/
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