import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.umich.mahira.fridgefriend.DisplayScannedItemActivity
import edu.umich.mahira.fridgefriend.R
import edu.umich.mahira.fridgefriend.RecipeStore
import edu.umich.mahira.fridgefriend.RecipeStore.getRecipes

class RecipeFragment:Fragment(R.layout.fragment_recipe) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        }
    }
}

