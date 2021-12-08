package edu.umich.mahira.fridgefriend

import RecipeFragment
import SavingsFragment
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
// bc shopactivity is no longer an activity it cannot use things from androidx.activity
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_shop.*

//import androidx.fragment.app.Fragment
//import kotlinx.android.synthetic.main.activity_shop.*

//val itemCollection = arrayListOf<Item?>() // use this to the items
//val LENGTH_MAX: Int = 10

class ShopActivity : AppCompatActivity() { // ?activity? // extends ?menu?
//@SuppressLint("UseRequireInsteadOfGet") // added to make viewModels work
//class ShopActivity : Fragment(R.layout.activity_shop) { ?frag?

    //public var itemText = String
    private val newItemActivityRequestCode = 1
    //private val editItemActivityRequestCode = 2
    private val setCatActivityRequestCode = 3
    private val shopViewModel: ShopView by viewModels { //?activity?
        ShopViewFactory((application as ItemsApplication).repository)
    }
    /*private val shopViewModel: ShopView by requireActivity().viewModels { // activity!!. // ?frag?
        ShopViewFactory((activity?.application as ItemsApplication).repository)
    }*/
    override fun onCreate(savedInstanceState: Bundle?) { //?activity?
    //override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //?frag?
        //shopViewModel.deleteAll()
        super.onCreate(savedInstanceState) //?activity?
        //super.onViewCreated(view, savedInstanceState) // ?frag?
        setContentView(R.layout.activity_shop) //?activity? wasnt in frag at all

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview) //?activity?
        val adapter = ShopListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this) //?activity?
        //recyclerview.adapter = adapter //?frag?
        //recyclerview.layoutManager = LinearLayoutManager(requireActivity()) //?frag?


        //Log.d("observe", shopViewModel.allItems.hasObservers().toString())
        //Log.d("active", shopViewModel.allItems.hasActiveObservers().toString())
        //Log.d("empty", shopViewModel.isEmpty().toString())
//        if (initialItems == 0) {
//            shopViewModel.insert(Shop("12 eggs"))
//            shopViewModel.insert(Shop("1 gallon milk"))
//            shopViewModel.insert(Shop("5 green apples"))
//        }
//        initialItems = 1

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
                    Toast.makeText(this@ShopActivity, "Item Deleted", Toast.LENGTH_SHORT).show() //?activity?
                    //Toast.makeText(requireActivity(), "Item Deleted", Toast.LENGTH_SHORT).show() //?frag?
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
                    startActivityForResult(Intent(this@ShopActivity, NewItemActivity::class.java), newItemActivityRequestCode) //?activity?
                    //startActivityForResult(Intent(requireActivity(), NewItemActivity::class.java), newItemActivityRequestCode) //?frag?
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
                    Toast.makeText(this@ShopActivity, temporary.item.toString(), Toast.LENGTH_LONG).show() //?activity?
                    Toast.makeText(this@ShopActivity, temporary.item.toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, temporary.item.toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, temporary.item.toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(this@ShopActivity, temporary.item.toString(), Toast.LENGTH_LONG).show()
                    // Toast.makeText(requireActivity(), temporary.item.toString(), Toast.LENGTH_LONG).show() //?frag?
                    //Toast.makeText(this@ShopActivity, itemText, Toast.LENGTH_LONG).setText(findViewById<EditText>(R.id.changed_item).text)
                }
            }
        }).attachToRecyclerView(recyclerView) /*?frag? recyclerview*/
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
        val add = findViewById<FloatingActionButton>(R.id.fab3) //?activity?
        add.setOnClickListener { //?activity?
        //fab3.setOnClickListener { //?frag?
            val intent = Intent(this@ShopActivity, NewItemActivity::class.java) //?activity?
            //val intent = Intent(requireActivity(), NewItemActivity::class.java) //?frag?
            Log.d("shopactivity", "before startActivity")
            startActivityForResult(intent, newItemActivityRequestCode)
        }
        val delete = findViewById<FloatingActionButton>(R.id.fab2) //?activity?
        delete.setOnClickListener { //?activity?
        //fab2.setOnClickListener { //?frag?
            shopViewModel.deleteAll()
        }
        val settings = findViewById<FloatingActionButton>(R.id.fab) //?activity?
        settings.setOnClickListener { //?activity?
        //fab.setOnClickListener { //?frag?
            val intent = Intent(this@ShopActivity, SetCatActivity::class.java) //?activity?
            //val intent = Intent(requireActivity(), SetCatActivity::class.java) //?frag?
            startActivityForResult(intent, setCatActivityRequestCode)
        }
        Log.d("shopact", "before all items update")
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        // val x = shopViewModel.temp

        // problem area
        shopViewModel.allItems.observe(owner = this) { items -> //?activity?
        //shopViewModel.allItems.observe(owner = requireActivity()) { items -> //?frag?
            // Update the cached copy of the items in the adapter.
            items/*?*/.let { adapter.submitList(it) }
        }
        Log.d("shopacti", "post update")


        // so that we can "stay in main activity while shifting to this activity
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.recipes -> startActivity(Intent(this@ShopActivity, MainActivity::class.java)) // maybe the packageContext as Main?
                R.id.my_fridge -> startActivity(Intent(this@ShopActivity, MainActivity::class.java))
                R.id.savings -> startActivity(Intent(this@ShopActivity, MainActivity::class.java))
            }
            true
        }

        /*val recipeFragment=RecipeFragment()
        val myFridgeFragment=MyFridgeFragment()
        val savingsFragment=SavingsFragment()
        //
        setCurrentFragment(recipeFragment, "recipeFragment")
        //
        bottomNavigationView.setOnClickListener {
            intent = Intent(this@ShopActivity, MainActivity::class.java)
            //startActivityForResult(intent, setCatActivityRequestCode)
        }*/
    }

    /*private fun setCurrentFragment(fragment: Fragment, tag: String)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment, tag)
            commit()
        }*/

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
                /*activity?.applicationContext!!, ?frag?*/
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}