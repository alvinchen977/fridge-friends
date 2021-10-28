package edu.umich.mahira.fridgefriend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope

// Annotates class to be a Room Database with a table (entity) of the Shop class
@Database(entities = arrayOf(Shop::class), version = 1, exportSchema = false)
// set exportSchema to a directory for Room to use to send our list to
public abstract class ShopRoomDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDao

    private class ShopDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.shopDao())
                }
            }
        }

        suspend fun populateDatabase(shopDao: ShopDao) {
            // Delete all content here.
            shopDao.deleteAll()

            // Add sample items: like apple
            // this might be where we can pull in items from recipes
            var item = Shop("apple")
            shopDao.insert(item)
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: ShopRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ShopRoomDatabase {
            // if INSTANCE is not null return it, else create database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShopRoomDatabase::class.java,
                    "item_database"
                ).addCallback(ShopDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}