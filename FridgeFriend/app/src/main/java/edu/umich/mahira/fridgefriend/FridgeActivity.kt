package edu.umich.mahira.fridgefriend

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import edu.umich.mahira.fridgefriend.databinding.ActivityFridgeBinding

val items = arrayListOf<Item?>() //use this to the items

class FridgeActivity : AppCompatActivity() {
    private lateinit var view: ActivityFridgeBinding
    private lateinit var itemListAdapter: GroceryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityFridgeBinding.inflate(layoutInflater)
        view.root.setBackgroundColor(Color.parseColor("#E0E0E0"))
        setContentView(view.root)

        itemListAdapter = GroceryListAdapter(this, items)
        view.GroceryListView.setAdapter(itemListAdapter)

    }
}