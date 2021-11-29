import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.fragment.app.Fragment
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.umich.mahira.fridgefriend.*
import edu.umich.mahira.fridgefriend.RecipeDetailActivity


class RecipeFragment:Fragment(R.layout.fragment_recipe) {
    private lateinit var listView: ListView

    fun newIntent(context: Context, recipe: Recipe): Intent {
        val detailIntent = Intent(context, RecipeDetailActivity::class.java)
        RecipeDetailStore.image = recipe.img!!
        detailIntent.putExtra("title", recipe.title)
        detailIntent.putExtra("ingredients", recipe.ingredients)
        detailIntent.putExtra("instructions", recipe.instructions)

        return detailIntent
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(activity?.applicationContext!!, "wait while recipes load", Toast.LENGTH_SHORT)
        RecipeStore.getRecipes(activity?.applicationContext!!){ it ->
//            val arrayTutorialType = object : TypeToken<Array<Array<String>>>() {}.type
//            var tutorials: Array<Any> = Gson().fromJson(it.toString(), arrayTutorialType)
//            val temp = tutorials[0] as Array<String>
//            val test = temp[1]
            val list: MutableList<Recipe> = arrayListOf()
            it.forEachIndexed  { idx, tut ->
                val tempRecipe = Recipe(title = null, ingredients = null, instructions = null, img = null)
                val tutArray = tut as Array<String>
                tutArray.forEachIndexed{ index, tut_actual ->
                    if (index.toInt() == 1){
                        println("> Item ${index}:\n${tut_actual}")
                        tempRecipe.title = tut_actual
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
            listView = view.findViewById<ListView>(R.id.recipe_list_view)
            val listItems = arrayOfNulls<Recipe>(list.size)
            for (i in 0 until list.size) {
                val recipe = list[i]
                listItems[i] = recipe
            }
            val adapter = RecipeAdapter(activity?.applicationContext!!, listItems)
            listView.adapter = adapter
            listView.setOnItemClickListener { _, _, position, _ ->
                // 1
                val selectedRecipe = listItems[position]
                // 2
                val detailIntent = newIntent(activity?.applicationContext!!, selectedRecipe!!)

                // 3
                startActivity(detailIntent)
            }
        }
    }
}

