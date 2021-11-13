package edu.umich.mahira.fridgefriend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

// val items = arrayListOf<Item?>() // use this to the items

class ShopActivity : AppCompatActivity() {

    private val newItemActivityRequestCode = 1
    private val shopViewModel: ShopView by viewModels {
        ShopViewFactory((application as ItemsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ShopListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@ShopActivity, NewItemActivity::class.java)
            startActivityForResult(intent, newItemActivityRequestCode)
        }

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        // val x = shopViewModel.temp
        shopViewModel.allItems.observe(owner = this) { items ->
            // Update the cached copy of the items in the adapter.
            items/*?*/.let { adapter.submitList(it) }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newItemActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(NewItemActivity.EXTRA_REPLY)?.let { reply ->
                val item = Shop(/*1, */reply)
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