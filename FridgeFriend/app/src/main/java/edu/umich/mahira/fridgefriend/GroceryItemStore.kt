package edu.umich.mahira.fridgefriend

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import org.json.JSONObject

object GroceryItemStore {
    private lateinit var queue: RequestQueue
    private const val serverUrl = "https://18.118.131.242/"

    fun postGrocery(context: Context, item: GroceryItem) {
        val jsonObj = mapOf(
            "image" to item.image,
        )
        val postRequest = JsonObjectRequest(
            Request.Method.POST,
            serverUrl+"postGrocery/", JSONObject(jsonObj),
            { Log.d("postGrocery", "grocery posted!") },
            { error -> Log.e("postGrocery", error.localizedMessage ?: "JsonObjectRequest error") }
        )

        if (!this::queue.isInitialized) {
            queue = newRequestQueue(context)
        }
        queue.add(postRequest)
    }

    fun postReceipt(context: Context, item: ReceiptItem) {
        val jsonObj = mapOf(
            "pdf" to item.pdf,
        )
        val postRequest = JsonObjectRequest(
            Request.Method.POST,
            serverUrl+"postReceipt/", JSONObject(jsonObj),
            { Log.d("postReceipt", "receipt posted!") },
            { error -> Log.e("postReceipt", error.localizedMessage ?: "JsonObjectRequest error") }
        )

        if (!this::queue.isInitialized) {
            queue = newRequestQueue(context)
        }
        queue.add(postRequest)
    }
}