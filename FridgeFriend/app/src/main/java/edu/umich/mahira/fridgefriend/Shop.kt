package edu.umich.mahira.fridgefriend

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// represents the SQLite table for grocery list with the properties
// as the columns in the table

@Entity(tableName = "shopList")
data class Shop(@PrimaryKey/*(autoGenerate = true) val id: Int,*/ @ColumnInfo(name = "item") val item: String)
    // might want to add val num: Int and val type: String later for amount and type (i.e. 1 oz)
