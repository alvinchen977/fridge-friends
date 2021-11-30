package edu.umich.mahira.fridgefriend

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {

    // flow always holds/caches latest version of data and notifies observers when data has changed
    // order by ASC is what makes it alphabetized
    // @Query("SELECT * FROM shopList ORDER BY item ASC") // or should it be cat + iName?
    @Query("SELECT * FROM shopList ORDER BY cat AND iName ASC") // shopList is the tableName of our entity in Shop.kt
    fun getAlphabetizedItems(): Flow<List<Shop>>

    // to organize items into 6 main categories (produce, staples, drinks, refrigerator, freezer, snacksCandy, misc)
    //@Query("SELECT * FROM shopList ORDER BY cat ASC")
    /*@Query("SELECT * FROM shopList ORDER BY cat ASC") // shopList is the tableName of our entity in Shop.kt
    fun getCategorizedItems(): Flow<List<Shop>>*/

    // queries can return a Cursor instead of wrapping the result in LiveData. this cursor could then be used
    // getColumnName(int columnIndex) returns the column name at zero-based column index
    // so if I wanted to know the category of a certain item and the cursor is on the row of that item
    // then just do Cursor.getColumnName(0) or do shop.getColumnNames() to find cat, amount, type, name of that item

    // getPosition() returns the current position of cursor in row set

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shop: Shop)
    //suspend fun insert(shop: Array<String>)
    //suspend fun insert(iAmount: Shop, iType: Shop, iName: Shop)
    //suspend fun insert(item: Shop)


    @Delete
    suspend fun delete(shop: Shop)
    //suspend fun delete(amount: iAmount, type: Shop().iType, name: Shop().iName)
    //suspend fun delete(Shop.iAmount, Shop.iType, Shop.iName)
    //suspend fun delete(item: Shop)

    // to be used to delete all of the items of specified category from list
    // also could be used to edit the name of a certain category so it can change the category name for all of these items
    // or maybe @Update (shopList.class) endl suspend fun updateCat(category: ShopSub) with ShopSub as a class in Shop.kt
    //@Query("SELECT * FROM shopList WHERE cat = :category") // shopList is the tableName of our entity in Shop.kt
    //suspend fun findItemsOfCat(category: String): List<Shop>

    @Query("DELETE FROM shopList") // shopList is the tableName of our entity in Shop.kt
    suspend fun deleteAll()
    // might be able to just DeleteTable(shopList) instead
}