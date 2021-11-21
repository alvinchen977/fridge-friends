import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.fragment.app.Fragment
import edu.umich.mahira.fridgefriend.R
import edu.umich.mahira.fridgefriend.RecipeAdapter
import edu.umich.mahira.fridgefriend.RecipeStore
import android.app.ProgressDialog
import android.widget.Toast


class RecipeFragment:Fragment(R.layout.fragment_recipe) {
    private lateinit var listView: ListView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(activity?.applicationContext!!, "wait while recipes load", Toast.LENGTH_SHORT)
        RecipeStore.getRecipes(activity?.applicationContext!!){ it ->
//            val arrayTutorialType = object : TypeToken<Array<Array<String>>>() {}.type
//            var tutorials: Array<Any> = Gson().fromJson(it.toString(), arrayTutorialType)
//            val temp = tutorials[0] as Array<String>
//            val test = temp[1]
            val list: MutableList<String> = arrayListOf()
            it.forEachIndexed  { idx, tut ->
                val tutArray = tut as Array<String>
                tutArray.forEachIndexed{ index, tut_actual ->
                    if (index.toInt() == 1){
                        println("> Item ${index}:\n${tut_actual}")
                        list.add(tut_actual)
                    }

                }

            }
            listView = view.findViewById<ListView>(R.id.recipe_list_view)
            val listItems = arrayOfNulls<String>(list.size)
            for (i in 0 until list.size) {
                val recipe = list[i]
                listItems[i] = recipe
            }
            val adapter = RecipeAdapter(activity?.applicationContext!!, listItems)
            listView.adapter = adapter

        }
    }
}

