package edu.umich.mahira.fridgefriend

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.Volley.newRequestQueue
import com.google.gson.Gson
import org.json.JSONObject
import java.time.Instant

object FridgeID {
    private lateinit var queue: RequestQueue
    var id: String? = null
    private const val serverUrl = "https://54.174.234.61/"

    fun signIn(context: Context, user: User, completion: (String) -> Unit): Unit {
        val jsonObj = mapOf(
            "username" to user.username,
            "password" to user.password,
        )
        val signInRequest = JsonObjectRequest(
            Request.Method.POST,
            FridgeID.serverUrl + "userLogin/", JSONObject(jsonObj),
            { response ->
                Log.d("Logged in", response.toString())
                completion(response["username"].toString())
            },
            { error ->
                Log.e(
                    "Unsuccessful log in ",
                    error.localizedMessage ?: "JsonObjectRequest error"
                )
            }
        )
        if (!this::queue.isInitialized) {
            FridgeID.queue = Volley.newRequestQueue(context)
        }
        FridgeID.queue.add(signInRequest)
    }

    fun createAccount(context: Context, user: User, completion: (String) -> Unit): Unit {
        val jsonObj = mapOf(
            "username" to user.username,
            "password" to user.password
        )
        val createRequest = JsonObjectRequest(
            Request.Method.POST,
            FridgeID.serverUrl + "userCreate/", JSONObject(jsonObj),
            { response ->
                Log.d("Created account", response["username"].toString())
                completion(response["username"].toString())
            },
            { error ->
                Log.e(
                    "Unsuccessful creation",
                    error.localizedMessage ?: "JsonObjectRequest error"
                )
            }
        )
        if (!this::queue.isInitialized) {
            FridgeID.queue = Volley.newRequestQueue(context)
        }
        FridgeID.queue.add(createRequest)
    }

    fun likeRecipe(context: Context, username: String, recipeId: String, completion: (String) -> Unit): Unit {
        val jsonObj = mapOf(
            "username" to username,
            "recipeid" to recipeId
        )
        val like = JsonObjectRequest(
            Request.Method.POST,
            serverUrl + "likeRecipe/", JSONObject(jsonObj),
            { response ->
                Log.d("Liked", response.toString())
                completion(response.toString())
            },
            { error ->
                Log.e(
                    "Liking unsuccessful ",
                    error.localizedMessage ?: "JsonObjectRequest error"
                )
            }
        )
        if (!this::queue.isInitialized) {
            FridgeID.queue = newRequestQueue(context)
        }
        FridgeID.queue.add(like)
    }
}