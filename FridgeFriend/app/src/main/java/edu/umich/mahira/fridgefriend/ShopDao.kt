package edu.umich.mahira.fridgefriend

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {
    @Query("SELECT * FROM shopList ORDER BY item ASC")
    fun getAlphabetizedItems(): Flow<List<Shop>>
    // fun getCatergorized(): to organize items into 6 main categories (produce, staples, drinks,
    // refrigerator, freezer, snacksCandy, misc)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Shop)

    @Query("DELETE FROM shopList")
    suspend fun deleteAll()
}