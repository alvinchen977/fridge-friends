package edu.umich.mahira.fridgefriend

import android.app.Application
import android.app.Activity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ItemsApplication : Application() {
    // scope will be torn down when processed so don't need to cancel
    val applicationScope = CoroutineScope(SupervisorJob())

    // lazy allows for database and repository to be created only when needed
    val database by lazy { ShopRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ShopRepository(database.shopDao()) }
}