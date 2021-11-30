package edu.umich.mahira.fridgefriend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.ItemTouchHelper




//val items = arrayListOf<Item?>() // use this to the items
//val LENGTH_MAX: Int = 10

class ShopActivity : AppCompatActivity() {
    //public var itemText = String
    private val newItemActivityRequestCode = 1
    //private val editItemActivityRequestCode = 2
    private val setCatActivityRequestCode = 3
    private val shopViewModel: ShopView by viewModels {
        ShopViewFactory((application as ItemsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //shopViewModel.deleteAll()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ShopListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.setOnDragListener(l: View.OnDragListener!)
        //recyclerView.setOnDragListener(l: ((View!, DragEvent!) -> Boolean)!)
        //recyclerView.setOnDragListener {v, event ->  ... } (l: ((View!, DragEvent!) -> Boolean)!)

        // to allow swipe right to delete or swipe left to edit
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) { //ItemTouchHelper.ACTION_STATE_SWIPE // ==
            /*override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                val intent = Intent(this@ShopActivity, EditItemActivity::class.java)
                startActivityForResult(intent, editItemActivityRequestCode)
                //EditItemActivity.item(adapter.getItemAt(viewHolder.adapterPosition))
                Toast.makeText(this@ShopActivity, "Item has been changed", Toast.LENGTH_SHORT).show()
            }*/

            override fun onMove(
                rcyclrView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                if (i == ItemTouchHelper.RIGHT) {
                    shopViewModel.delete(adapter.getItemAt(viewHolder.adapterPosition))
                    Toast.makeText(this@ShopActivity, "Item Deleted", Toast.LENGTH_SHORT).show()
                }
                else {
//                    shopViewModel.changingItem.observe(owner = this) { hasItem -> // problem area
//                        // Update the cached copy of the items in the adapter.
//                        hasItem/*?*/.let { (adapter.getItemAt(viewHolder.adapterPosition)).item  }
//                    }

                    //val itemText: CharSequence = (adapter.getItemAt(viewHolder.adapterPosition)).item
                    val temporary = adapter.getItemAt(viewHolder.adapterPosition)
                    //val itemText = temporary.iAmount.toString() + " " + temporary.iType + " " + temporary.iName  ?split?

                    //val changess: TextView = findViewById<EditText>(R.id.changed_item)
                    //changess.text = itemText
                    shopViewModel.delete(adapter.getItemAt(viewHolder.adapterPosition))
                    /*val intentForEdit = Intent(this@ShopActivity, EditItemActivity::class.java) for editItemActivity************************
                    intentForEdit.putExtra("itemToChange", itemText)
                    startActivityForResult(intentForEdit,editItemActivityRequestCode)*/
                    startActivityForResult(Intent(this@ShopActivity, NewItemActivity::class.java), newItemActivityRequestCode)
                    /*recyclerView.setOnClickListener {
                        val intent = Intent(this@ShopActivity, NewItemActivity::class.java)
                        startActivityForResult(intent, newItemActivityRequestCode)
                    }*/

                    //Toast.makeText(this@ShopActivity, "Update Item", Toast.LENGTH_SHORT).show()
                    // allows item being edited to stay on the screen for about 15 seconds
                    /*Toast.makeText(this@ShopActivity, itemText, Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, itemText, Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, itemText, Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, itemText, Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, itemText, Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, itemText, Toast.LENGTH_LONG).show() ?split?*/
                    Toast.makeText(this@ShopActivity, temporary.item.toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, temporary.item.toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, temporary.item.toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, temporary.item.toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, temporary.item.toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, temporary.item.toString(), Toast.LENGTH_LONG).show()
                    //Toast.makeText(this@ShopActivity, itemText, Toast.LENGTH_LONG).setText(findViewById<EditText>(R.id.changed_item).text)
                }
            }
        }).attachToRecyclerView(recyclerView)

        //recyclerView.onNestedPrePerformAccessibilityAction(target: View!, action: Int, args: Bundle!)
        //recyclerView.onProvideAutofillStructure(structure: ViewStructure!, flags: Int)
        //recyclerView.onScreenStateChanged(screenState: Int) //--- might use for edit categories
        //recyclerView.onViewAdded(child: View!)
        //recyclerView.onTouchEvent(e: MotionEvent!)
        //recyclerView.onClick() = true
//        recyclerView.setOnClickListener {
//            val intent = Intent(this@ShopActivity, EditItemActivity::class.java)
//            startActivityForResult(intent, editItemActivityRequestCode)
//        }

//        val current_item = adapter.getItemAt((RecyclerView.ViewHolder).adapterPosition)
//        recyclerView.setOnClickListener(current_item) {
//            val intent = Intent(this@ShopActivity, NewItemActivity::class.java)
//            startActivityForResult(intent, newItemActivityRequestCode)
//        }

        val add = findViewById<FloatingActionButton>(R.id.fab3)
        add.setOnClickListener {
            val intent = Intent(this@ShopActivity, NewItemActivity::class.java)
            Log.d("shopactivity", "before startActivity")
            startActivityForResult(intent, newItemActivityRequestCode)
        }

        val delete = findViewById<FloatingActionButton>(R.id.fab2)
        delete.setOnClickListener {
            shopViewModel.deleteAll()
        }

        val settings = findViewById<FloatingActionButton>(R.id.fab)
        settings.setOnClickListener {
            val intent = Intent(this@ShopActivity, SetCatActivity::class.java)
            startActivityForResult(intent, setCatActivityRequestCode)
        }

        Log.d("shopact", "before all items update")
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        // val x = shopViewModel.temp
        shopViewModel.allItems.observe(owner = this) { items -> // problem area
            // Update the cached copy of the items in the adapter.
            items/*?*/.let { adapter.submitList(it) }
        }
        Log.d("shopacti", "post update")

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

//        if (resultCode != RESULT_CANCELED /*&& intentData != null*/) {
//            if (requestCode == newItemActivityRequestCode && resultCode == Activity.RESULT_OK && intentData != null/* && resultCode != RESULT_CANCELED*/) {
//                intentData?.getStringExtra(NewItemActivity.EXTRA_REPLY)?.let { reply ->
//                    val item = Shop(/*1, */reply)
//                    shopViewModel.insert(item)
//                }
//            } else {
//                Toast.makeText(
//                    applicationContext,
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
        if (requestCode == newItemActivityRequestCode && resultCode == Activity.RESULT_OK/* && intentData != null && resultCode != RESULT_CANCELED*/) {
            intentData?.getStringExtra(NewItemActivity.EXTRA_REPLY)?.let { reply ->
                //val item = reply1
                //val item = Shop(reply,1,"lb")

                shopViewModel.insert(Shop(reply.toLowerCase()/*,1,"lb"*/)) // problem area
            }
            /*intentData?.getStringExtra(NewItemActivity.EXTRA_REPLY1)?.let { reply1 ->
                val item1 = reply1
            }
            intentData?.getStringExtra(NewItemActivity.EXTRA_REPLY2)?.let { reply2 ->
                val item2 = reply2
            }
            intentData?.getStringExtra(NewItemActivity.EXTRA_REPLY3)?.let { reply3 ->
                val item3 = reply3
            }
            shopViewModel.insert(Shop(item1,item2,item3))*/
            //val reply0 = intentData?.getStringExtra(NewItemActivity.EXTRA_REPLY0) ?cat?
            /*val reply1 = intentData?.getStringExtra(NewItemActivity.EXTRA_REPLY1) ?split?
            val reply2 = intentData?.getStringExtra(NewItemActivity.EXTRA_REPLY2)
            val reply3 = intentData?.getStringExtra(NewItemActivity.EXTRA_REPLY3)
            //val cat : Int = shopViewModel.determineCategory(reply0.toString())
            shopViewModel.insert(Shop(/*reply0.toString(), ?cat? */Integer.parseInt(reply1.toString()),
                reply2.toString().toLowerCase(),reply3.toString().toLowerCase()))*/

//            intentData?.getIntExtra(NewItemActivity.EXTRA_REPLY)?.let { (reply2) ->
//                val item = reply2
//                //val item = Shop(reply,1,"lb")
//                //shopViewModel.insert(item) // problem area
//            }
        }/* else if (requestCode == editItemActivityRequestCode && resultCode == Activity.RESULT_OK) {
            /*intentData?.getStringExtra(EditItemActivity.EXTRA_REPLY)?.let { reply ->
                shopViewModel.insert(Shop(reply.toLowerCase()))
            }*/
            val reply1 = intentData?.getStringExtra(EditItemActivity.EXTRA_REPLY1)
            val reply2 = intentData?.getStringExtra(EditItemActivity.EXTRA_REPLY2)
            val reply3 = intentData?.getStringExtra(EditItemActivity.EXTRA_REPLY3)
            shopViewModel.insert(Shop(Integer.parseInt(reply1.toString()),reply2.toString().toLowerCase(),reply3.toString().toLowerCase()))
        }*/ else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}