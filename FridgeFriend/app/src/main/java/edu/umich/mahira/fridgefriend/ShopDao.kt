package edu.umich.mahira.fridgefriend

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {

    // flow always holds/caches latest version of data and notifies observers when data has changed
    // order by ASC is what makes it alphabetized
    @Query("SELECT * FROM shopList ORDER BY item ASC")
    fun getAlphabetizedItems(): Flow<List<Shop>>
    // fun getCatergorized(): to organize items into 6 main categories (produce, staples, drinks,
    // refrigerator, freezer, snacksCandy, misc)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Shop)

    @Query("DELETE FROM shopList")
    suspend fun deleteAll()
}