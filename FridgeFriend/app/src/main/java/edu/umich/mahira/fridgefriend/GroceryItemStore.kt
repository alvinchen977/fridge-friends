package edu.umich.mahira.fridgefriend

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import org.json.JSONObject

object GroceryItemStore {
    private lateinit var queue: RequestQueue
    private const val serverUrl = "https://54.174.234.61/"
    fun postGrocery(context: Context, item: GroceryItem, completion: (String) -> Unit): String {
        val jsonObj = mapOf(
            "image" to item.image,
        )
        val postRequest = JsonObjectRequest(
            Request.Method.POST,
            serverUrl + "postGrocery/", JSONObject(jsonObj),
            { response ->
                Log.d("postGrocery", response.toString())
                completion(response.toString())
            },
            { error ->
                Log.e(
                    "postGrocery",
                    error.localizedMessage ?: "JsonObjectRequest error"
                )
            }
        )

        if (!this::queue.isInitialized) {
            queue = newRequestQueue(context)
        }
        queue.add(postRequest)
        return "temporaryString"
    }

    fun postReceipt(context: Context, item: ReceiptItem) {
        val jsonObj = mapOf(
            "image" to item.pdf,
        )
        val postRequest = JsonObjectRequest(
            Request.Method.POST,
            serverUrl + "postReceipt/", JSONObject(jsonObj),
            Response.Listener { response ->
                Log.d("postReceipt", response.toString());
            },
            Response.ErrorListener { error ->
                Log.e(
                    "postReceipt",
                    error.localizedMessage ?: "JsonObjectRequest error"
                )
            }
        )

        if (!this::queue.isInitialized) {
            queue = newRequestQueue(context)
        }
        queue.add(postRequest)
    }
}