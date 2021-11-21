package edu.umich.mahira.fridgefriend

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import org.json.JSONArray




object RecipeStore {
    private lateinit var queue: RequestQueue
    private const val serverUrl = "https://54.174.234.61/"
    fun getRecipes(context: Context, completion: (Array<Any>) -> Unit): String {
        val getRequest = JsonObjectRequest(
            Request.Method.GET,
            RecipeStore.serverUrl + "findRecipeByDefault/", null,
            { response ->
                val arrayTutorialType = object : TypeToken<Array<Array<String>>>() {}.type
                var tutorials: Array<Any> = Gson().fromJson(response["recipes"].toString(), arrayTutorialType)
                val temp = tutorials[0] as Array<String>
                val test = temp[1]
                Log.d("getRecipes", test)
                tutorials.forEachIndexed  { idx, tut ->
                    val tutArray = tut as Array<String>
                    tutArray.forEachIndexed{ index, tut_actual ->
                        println("> Item ${index}:\n${tut_actual}")
                    }

                }
                completion(tutorials)
            },
            { error ->
                Log.e(
                    "getRecipes",
                    error.localizedMessage ?: "JsonObjectRequest error"
                )
            }
        )

        if (!this::queue.isInitialized) {
            RecipeStore.queue = Volley.newRequestQueue(context)
        }
        RecipeStore.queue.add(getRequest)
        return "temp"
    }
}