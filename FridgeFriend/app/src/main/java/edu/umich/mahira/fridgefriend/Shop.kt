package edu.umich.mahira.fridgefriend

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//import androidx.room.Ignore
import androidx.room.Index
//import androidx.room.Insert
//import androidx.room.MultiInstanceInvalidationService /Callback
//import androidx.room.OnConflictStrategy
//import androidx.room.RewriteQueriesToDropUnusedColumns
//import androidx.room.Update
import androidx.lifecycle.*

// represents the SQLite table for grocery list with the properties
// as the columns in the table
// val cat: LiveData<String> ... etc.
@Entity(tableName = "shopList") // represents a SQLite table
data class Shop(/*@PrimaryKey (autoGenerate = true) val id: Int, @PrimaryKey*/ var cat: String, @ColumnInfo(name = "iAmount") var iAmount: Int,
                @ColumnInfo(name = "iType") var iType: String, @PrimaryKey @ColumnInfo(name = "iName") var iName: String) {

    // every entity needs a PrimaryKey
    // ColumnInfo represents the name of the column in the table

    //var indices: Array<Index>

    // cat for category :)
    // amount and type (i.e. 1 oz)

    //val lookingAtItem: LiveData<type> = .asLiveData()
//    val iName: LiveData<String> = Shop(item).iName
//    val iAmount: LiveData<Int> = Shop(item).iAmount
//    val iType: LiveData<String> = Shop(item).iType
//    var iName: String = Shop(item).iName
//    var iAmount: Int = Shop(item).iAmount
//    var iType: String = Shop(item).iType

    /*val lookItem: String = Shop(iAmount,iType,iName).iAmount.toString() + " " +
            Shop(iAmount,iType,iName).iType + " " + Shop(iAmount,iType,iName).iName*/
    /*public lookItem : String {
        return iAmount.toString() + " " + iType + " " + iName
    }*/
}

// specifically for updating categories in ShopDao
/*data class ShopSub(var cat: String, var iName: String) {

}*/

/*@Entity(tableName = "shopList")
    data class Shop {
        @PrimaryKey
        val cat: Int;
        @ColumnInfo(name = "iAmount")
        val iAmount: Int;
        @ColumnInfo(name = "iType")
        val iType: String;
        @ColumnInfo(name = "iName")
        val iName: String;

        Shop(cat: Int, iAmount: Int, iType: String, iName: String) {
            this.cat = cat;
            this.iAmount = iAmount;
            this.iType = iType;
            this.iName = iName;
        }


    }*/