package edu.umich.mahira.fridgefriend

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import okhttp3.*
import kotlin.reflect.full.declaredMemberProperties

object ReceiptItemStore {
    private lateinit var queue: RequestQueue
    private const val serverUrl = "https://54.174.234.61/"
    private val client = OkHttpClient()
    val receipts = arrayListOf<Int?>() //use this to the items
    private val nFields = Int::class.declaredMemberProperties.size

    fun postTotalReceipt(context: Context, total: Int, completion: (String) -> Unit) {
        val jsonObj = mapOf(
            "username" to "test", //change
            "totalReceipt" to total
        )
        val postRequest = JsonObjectRequest(
            Request.Method.POST,
            serverUrl + "postToReceipt/", JSONObject(jsonObj),
            { response ->
                Log.d("postToReceipt/", response.toString())
                completion(response.toString())
            },
            { error ->
                Log.e(
                    "postToReceipt/",
                    error.localizedMessage ?: "JsonObjectRequest error"
                )
            }
        )

        if (!this::queue.isInitialized) {
            queue = Volley.newRequestQueue(context)
        }
        queue.add(postRequest)
    }

    fun getReceipts(context: Context, completion: () -> Unit) {
        val request = okhttp3.Request.Builder()
            .url(serverUrl +"getReceiptsItem/")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("getReceipts", "Failed GET request")
                completion()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val itemsReceived = try { JSONObject(response.body?.string() ?: "").getJSONArray("items") } catch (e: JSONException) { JSONArray() }

                    receipts.clear()
                    for (i in 0 until itemsReceived.length()) {
                        val chattEntry = itemsReceived[i] as JSONArray
                        if (chattEntry.length() == nFields) {
                            receipts.add(chattEntry[0] as Int?)
                        } else {
                            Log.e("getReceipts", "Received unexpected number of fields " + chattEntry.length().toString() + " instead of " + nFields.toString())
                        }
                    }
                    completion()
                }
            }
        })
    }

}