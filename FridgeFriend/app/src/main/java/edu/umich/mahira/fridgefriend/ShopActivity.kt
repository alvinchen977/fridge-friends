// temporary: might use room instead for shopping list

package edu.umich.mahira.fridgefriend

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
// import edu.umich.mahira.fridgefriend.databinding.ActivityFridgeBinding

// val items = arrayListOf<Item?>() // use this to the items

class ShopActivity : AppCompatActivity() {
    // private lateinit var view: ActivityFridgeBinding
    // private lateinit var itemListAdapter: GroceryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view = ActivityFridgeBinding.inflate(layoutInflater)
        // view.root.setBackgroundColor(Color.parseColor("#e2e9e5"))
        setContentView(R.layout.activity_shop)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ShopListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}
