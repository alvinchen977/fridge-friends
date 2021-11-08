package edu.umich.mahira.fridgefriend

import GroceryListFragment
import MyFridgeFragment
import RecipeFragment
import SavingsFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    fun startPost(view: View?) = startActivity(Intent(this, PostActivity::class.java))
    fun viewFridge(view: View?) = startActivity(Intent(this, FridgeActivity::class.java))
    fun viewGraph(view: View?) = startActivity(Intent(this, SpendingGraphActivity::class.java))
    fun startReminder(view: View?) = startActivity(Intent(this, ReminderActivity::class.java))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recipeFragment=RecipeFragment()
        val groceryListFragment=GroceryListFragment()
        val myFridgeFragment=MyFridgeFragment()
        val savingsFragment=SavingsFragment()

        setCurrentFragment(recipeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->startActivity(Intent(this, MainActivity::class.java))
                R.id.grocery_list->setCurrentFragment(groceryListFragment)
                R.id.my_fridge->startActivity(Intent(this, FridgeActivity::class.java))
                R.id.savings->startActivity(Intent(this, SpendingGraphActivity::class.java))
            }
            true
        }

    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

}



