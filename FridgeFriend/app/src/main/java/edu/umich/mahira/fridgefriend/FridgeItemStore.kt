package edu.umich.mahira.fridgefriend

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.reflect.full.declaredMemberProperties


object FridgeItemStore {
    val items = arrayListOf<Item?>()
    private val client = OkHttpClient()
    private val nFields = Item::class.declaredMemberProperties.size
    private lateinit var queue: RequestQueue
    private const val serverUrl = "https://54.174.234.61/"
    // Need to change the name of the routs

    fun postItem(context: Context, item: Item, completion: (String) -> Unit) {
        val jsonObj = mapOf(
            "username" to "test", //change
            "itemName" to item.name,
            "quantity" to item.quantity
        )
        Log.d("json",jsonObj.toString())
        val postRequest = JsonObjectRequest(
            Request.Method.POST,
            serverUrl + "postToFridge/", JSONObject(jsonObj),
            { response ->
                Log.d("postToFridge", response.toString())
                completion(response.toString())
            },
            { error ->
                Log.e(
                    "postToFridge",
                    error.localizedMessage ?: "JsonObjectRequest error"
                )
            }
        )

        if (!this::queue.isInitialized) {
            FridgeItemStore.queue = Volley.newRequestQueue(context)
        }
        FridgeItemStore.queue.add(postRequest)
    }

    fun deleteItem(context: Context, name: String, completion: (String) -> Unit) {
        val jsonObj = mapOf(
            "username" to "test", //change
            "itemName" to name,
        )
        val postRequest = JsonObjectRequest(
            Request.Method.POST,
            FridgeItemStore.serverUrl + "deleteFromFridge/", JSONObject(jsonObj),
            { response ->
                Log.d("deleteFromFridge", response.toString())
                completion(response.toString())
            },
            { error ->
                Log.e(
                    "deleteFromFridge",
                    error.localizedMessage ?: "JsonObjectRequest error"
                )
            }
        )

        if (!this::queue.isInitialized) {
            FridgeItemStore.queue = Volley.newRequestQueue(context)
        }
        FridgeItemStore.queue.add(postRequest)
    }

    fun updateItem(context: Context, itemBefore: String,itemAfter: String ,completion: (String) -> Unit) {
        val jsonObj = mapOf(
            "username" to "test", //change
            "nameBefore" to itemBefore,
            "nameAfter" to itemAfter
        )
        val postRequest = JsonObjectRequest(
            Request.Method.POST, // maybe change this
            FridgeItemStore.serverUrl + "updateFridgeItem/", JSONObject(jsonObj),
            { response ->
                Log.d("updateFridgeItem", response.toString())
                completion(response.toString())
            },
            { error ->
                Log.e(
                    "updateFridgeItem",
                    error.localizedMessage ?: "JsonObjectRequest error"
                )
            }
        )

        if (!this::queue.isInitialized) {
            FridgeItemStore.queue = Volley.newRequestQueue(context)
        }
        FridgeItemStore.queue.add(postRequest)
    }

    fun getItems(context: Context, completion: () -> Unit) {
        val jsonObj = mapOf(
            "username" to "test", //change
        )
        val request = JsonObjectRequest(
        Request.Method.POST, // maybe change this
        FridgeItemStore.serverUrl + "getUserFridge/", JSONObject(jsonObj),
        { response ->

            Log.d("getUserFridge", response.toString())
            val itemsReceived = try { response.getJSONArray("items") } catch (e: JSONException) { JSONArray() }
            items.clear()
            for (i in 0 until itemsReceived.length()) {
                Log.d("itemsReceived", itemsReceived[i].toString())
                val chattEntry = itemsReceived[i] as JSONObject
                for(key in chattEntry.keys() ){
                    val quantity: Int = chattEntry[key] as Int
                    items.add(Item(name = key,
                        quantity = quantity as Int?,
                    ))
                }

            }
            completion()
        },
        { error ->
            Log.e(
                "updateFridgeItem",
                error.localizedMessage ?: "JsonObjectRequest error"
            )
        }
        )
        if (!this::queue.isInitialized) {
            FridgeItemStore.queue = Volley.newRequestQueue(context)
        }
        FridgeItemStore.queue.add(request)
    }

}