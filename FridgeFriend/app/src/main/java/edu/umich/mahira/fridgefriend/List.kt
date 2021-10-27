// represents the SQLite table for grocery list with the properties
// as the columns in the table

@Entity(tableName = "shopList")
data class List(val list: String) {
    // might want to add val num: Int and val type: String later for amount and type (i.e. 1 oz)


}
