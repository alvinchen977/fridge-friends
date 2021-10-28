package edu.umich.mahira.fridgefriend

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// this file helps to decide whether to get data from a network (adding items from a
// selected recipe) or from a local database (user inputted items)

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ShopRepository(private val shopDao: ShopDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allItems: Flow<List<Shop>> = shopDao.getAlphabetizedItems()
    // again here might be able to use a category function in place of getAlpha...()
    // to organize items into (produce, staples, drinks, refrigerator, freezer, snacksCandy, misc)

    // By default Room runs suspend queries off the main thread, therefore, we don't need to implement
    // anything else to ensure we're not doing long running database work off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(item: Shop) {
        shopDao.insert(item)
    }
}