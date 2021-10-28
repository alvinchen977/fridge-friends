package edu.umich.mahira.fridgefriend

import android.app.Application

class ItemsApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { ShopRoomDatabase.getDatabase(this) }
    val repository by lazy { ShopRepository(database.shopDao()) }
}