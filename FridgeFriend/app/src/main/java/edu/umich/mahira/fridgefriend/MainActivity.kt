package edu.umich.mahira.fridgefriend

import GroceryListFragment
import RecipeFragment
import SavingsFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView

class MainActivity : AppCompatActivity() {
    fun viewGraph(view: View?) = startActivity(Intent(this, SpendingGraphActivity::class.java))
    fun startReminder(view: View?) = startActivity(Intent(this, ReminderActivity::class.java))

    private lateinit var listView: ListView


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
                R.id.recipes->setCurrentFragment(recipeFragment)
                R.id.grocery_list->setCurrentFragment(groceryListFragment)
                R.id.my_fridge->setCurrentFragment(myFridgeFragment)
                R.id.savings->setCurrentFragment(savingsFragment)
            }
            true
        }

    }

    /*private fun refreshFragment(context: Context?) {
        context?.let {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragmentManager?.let {
                val currentFragment = fragmentManager.findFragmentById(R.id.flFragment)
                currentFragment?.let {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }

    }*/

    fun replaceFridgeFragment() {
        val myFridgeFragment=MyFridgeFragment()
        setCurrentFragment(myFridgeFragment)
    }


    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

}



