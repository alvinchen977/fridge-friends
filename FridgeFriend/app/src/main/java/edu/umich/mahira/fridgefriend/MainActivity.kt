package edu.umich.mahira.fridgefriend

import GroceryListFragment
import RecipeFragment
import SavingsFragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    fun viewGraph(view: View?) = startActivity(Intent(this, SpendingGraphActivity::class.java))
    fun startReminder(view: View?) = startActivity(Intent(this, ReminderActivity::class.java))

    private lateinit var listView: ListView

    val recipeFragment=RecipeFragment()
    val groceryListFragment=GroceryListFragment()
    val myFridgeFragment=MyFridgeFragment()
    val savingsFragment=SavingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val recipeFragment=RecipeFragment()
        val groceryListFragment=GroceryListFragment()
        val myFridgeFragment=MyFridgeFragment()
        val savingsFragment=SavingsFragment()*/

        setCurrentFragment(recipeFragment, "recipeFragment")

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.recipes->setCurrentFragment(recipeFragment, "recipeFragment")
                R.id.grocery_list->setCurrentFragment(groceryListFragment, "groceryListFragment" )
                R.id.my_fridge->setCurrentFragment(myFridgeFragment, "myFridgeFragment")
                R.id.savings->setCurrentFragment(savingsFragment, "savingsFragment")
            }
            true
        }

    }

     fun setCurrentFragment(fragment:Fragment, tag: String)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment, tag)
            commit()
        }

}



