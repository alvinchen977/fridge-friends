// temporary: might use room instead for shopping list

package edu.umich.mahira.fridgefriend

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

// import edu.umich.mahira.fridgefriend.databinding.ActivityFridgeBinding

// val items = arrayListOf<Item?>() // use this to the items

class ShopActivity : AppCompatActivity() {
    // private lateinit var view: ActivityFridgeBinding
    // private lateinit var itemListAdapter: GroceryListAdapter

    private val newItemActivityRequestCode = 1

    // in ShopView.kt
    private val shopViewModel: ShopView by viewModels {
        ShopViewFactory((application as ItemsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // view = ActivityFridgeBinding.inflate(layoutInflater)
        // view.root.setBackgroundColor(Color.parseColor("#e2e9e5"))

        setContentView(R.layout.activity_shop)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ShopListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        ShopView.allItems.observe(owner = this) { items ->
            // Update the cached copy of the items in the adapter.
            items?.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@ShopActivity, NewItemActivity::class.java)
            startActivityForResult(intent, newItemActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newItemActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(NewItemActivity.EXTRA_REPLY)?.let { reply ->
                val item = Shop(reply)
                shopViewModel.insert(item)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
