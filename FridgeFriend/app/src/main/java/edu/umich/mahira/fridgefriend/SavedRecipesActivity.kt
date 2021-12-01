package edu.umich.mahira.fridgefriend

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class SavedRecipesActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    fun newIntent(context: Context, recipe: Recipe): Intent {
        val detailIntent = Intent(context, RecipeDetailActivity::class.java)
        RecipeDetailStore.image = recipe.img!!
        detailIntent.putExtra("title", recipe.title)
        detailIntent.putExtra("ingredients", recipe.ingredients)
        detailIntent.putExtra("instructions", recipe.instructions)
        detailIntent.putExtra("recipeId", recipe.recipeId)

        return detailIntent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_recipes)
        RecipeStore.getSavedRecipes(applicationContext){ it ->
//            val arrayTutorialType = object : TypeToken<Array<Array<String>>>() {}.type
//            var tutorials: Array<Any> = Gson().fromJson(it.toString(), arrayTutorialType)
//            val temp = tutorials[0] as Array<String>
//            val test = temp[1]
            val list: MutableList<Recipe> = arrayListOf()
            val temp: Array<Any> = it[0] as Array<Any>
            temp.forEachIndexed  { idx, tut ->
                val tempRecipe = Recipe(title = null, ingredients = null, instructions = null, img = null, recipeId = null)
                val tutArray = tut as Array<String>
                tutArray.forEachIndexed{ index, tut_actual ->
                    if (index.toInt() == 1){
                        println("> Item ${index}:\n${tut_actual}")
                        tempRecipe.title = tut_actual
                    }
                    else if (index.toInt() == 0){
                        tempRecipe.recipeId = tut_actual
                    }
                    else if (index.toInt() == 2){
//                        val arrayTutorialType = object : TypeToken<Array<String>>() {}.type
//                        var tutorials: Array<Any> = Gson().fromJson(tut_actual, arrayTutorialType)
                        tempRecipe.ingredients = tut_actual
                    }
                    else if (index.toInt() == 3){
                        tempRecipe.instructions = tut_actual
                    }
                    else if (index.toInt() == 4){
                        tempRecipe.img = tut_actual
                    }

                }
                list.add(tempRecipe)
            }
            listView = findViewById<ListView>(R.id.saved_recipe_list_view)
            val listItems = arrayOfNulls<Recipe>(list.size)
            for (i in 0 until list.size) {
                val recipe = list[i]
                listItems[i] = recipe
            }
            val adapter = RecipeAdapter(applicationContext, listItems)
            listView.adapter = adapter
            listView.setOnItemClickListener { _, _, position, _ ->
                // 1
                val selectedRecipe = listItems[position]
                // 2
                val detailIntent = newIntent(applicationContext, selectedRecipe!!)

                // 3
                startActivity(detailIntent)
            }
        }
    }
}