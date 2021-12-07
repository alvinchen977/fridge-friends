package edu.umich.mahira.fridgefriend

import GroceryListFragment
import RecipeFragment
import SavingsFragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.* //??

//var initialItems = 0

class MainActivity : AppCompatActivity() {
    /* previously
    fun startPost(view: View?) = startActivity(Intent(this, PostActivity::class.java))
    fun startShop(view: View?) = startActivity(Intent(this, ShopActivity::class.java))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
   }
}*/
    fun startReminder(view: View?) = startActivity(Intent(this, ReminderActivity::class.java))

    private lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recipeFragment=RecipeFragment()
        //val groceryListFragment=ShopActivity() //?frag?
        val myFridgeFragment=MyFridgeFragment()
        val savingsFragment=SavingsFragment()

        setCurrentFragment(recipeFragment, "recipeFragment")

        //val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView) // fixed? ??

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.recipes->setCurrentFragment(recipeFragment, "recipeFragment")
                //R.id.grocery_list->setCurrentFragment(groceryListFragment, "groceryListFragment") //?frag?
                R.id.grocery_list-> startActivity(Intent(this,ShopActivity::class.java)) // ?activity?
                R.id.my_fridge->setCurrentFragment(myFridgeFragment, "myFridgeFragment")
                R.id.savings->setCurrentFragment(savingsFragment, "savingsFragment")
            }
            true
        }

        /*Log.d("hi there", "HI PEOPLE OF FRIDGE FRIENDS LET ME HERE YA SAY HEYYYYYY")
        Log.d("hello", myFridgeFragment.getActivity().toString())*/

    }

     private fun setCurrentFragment(fragment:Fragment, tag: String)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment, tag)
            commit()
        }

}
