package edu.umich.mahira.fridgefriend

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// represents the SQLite table for grocery list with the properties
// as the columns in the table

@Entity(tableName = "shopList")
data class Shop(@PrimaryKey/*(autoGenerate = true) val id: Int,*/ @ColumnInfo(name = "item") val item: String/*,
                @ColumnInfo(name = "num") val num: Int, @ColumnInfo(name = "type") val type: String*/)
    // amount and type (i.e. 1 oz)
    // I'm thinking we could turn the id into a categorization thing. 1 means produce. 2 means dairy. etc.
