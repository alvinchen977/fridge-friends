package edu.umich.mahira.fridgefriend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// makes class into a Room Database with a table (entity) of Shop class
// set exportSchema to a directory for Room to use to send our list to
@Database(entities = arrayOf(Shop::class), version = 1/*, exportSchema = false*/)
abstract class ShopRoomDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDao

    /*
    used here is the Singleton pattern, in which we ensure class has just one instance (bc don't
    want constructor to make new object every call) and provide global access point to that instance
    */
    companion object {
        @Volatile
        private var INSTANCE: ShopRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ShopRoomDatabase {
            // if INSTANCE is not null return it, else create database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShopRoomDatabase::class.java,
                    "item_database"
                )
                    // wipes and rebuild instead of migrating if no migration object exists
                    .fallbackToDestructiveMigration()
                    .addCallback(ShopDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class ShopDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

            // override onCreate method to populate database
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // the following line ensures data is NOT kept through app restarts (to change just comment it out)
                INSTANCE?.let { database ->
                    //scope.launch {
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.shopDao())
                    }
                }
            }
        }

        // populate database in new coroutine
        suspend fun populateDatabase(shopDao: ShopDao) {

            // starts app w/ clean database every time (necessary bc dont only populate on creation)
            shopDao.deleteAll()

            // Add sample items: like apple
            // this might be where we can pull in items from recipes
            var item = Shop(/*1, */"apple")
            shopDao.insert(item)
        }
    }
}