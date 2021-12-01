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
import java.io.IOException
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
            Request.Method.DELETE,
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
            Request.Method.PATCH, // maybe change this
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
        val request = okhttp3.Request.Builder()
            .url(serverUrl+"getFridgeItem/")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("getItems", "Failed GET request")
                completion()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val itemsReceived = try { JSONObject(response.body?.string() ?: "").getJSONArray("items") } catch (e: JSONException) { JSONArray() }

                    items.clear()
                    for (i in 0 until itemsReceived.length()) {
                        val chattEntry = itemsReceived[i] as JSONArray
                        if (chattEntry.length() == nFields) {
                            items.add(Item(name = chattEntry[0].toString(),
                                quantity = chattEntry[1] as Int?,
                            ))
                        } else {
                            Log.e("getItems", "Received unexpected number of fields " + chattEntry.length().toString() + " instead of " + nFields.toString())
                        }
                    }
                    completion()
                }
            }
        })
    }

}