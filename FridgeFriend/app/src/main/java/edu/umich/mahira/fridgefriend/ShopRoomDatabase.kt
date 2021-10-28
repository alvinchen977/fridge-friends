package edu.umich.mahira.fridgefriend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Shop class
@Database(entities = arrayOf(Shop::class), version = 1, exportSchema = false)
// set exportSchema to a directory for Room to use to send our list to
public abstract class ShopRoomDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: ShopRoomDatabase? = null

        fun getDatabase(context: Context): ShopRoomDatabase {
            // if INSTANCE is not null return it, else create database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShopRoomDatabase::class.java,
                    "item_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}