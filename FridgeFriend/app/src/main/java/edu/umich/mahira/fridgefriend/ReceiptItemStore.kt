package edu.umich.mahira.fridgefriend

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
            "username" to FridgeID.id.toString(), //change
            "total" to total
        )
        val postRequest = JsonObjectRequest(
            Request.Method.POST,
            serverUrl + "postReceipt/", JSONObject(jsonObj),
            { response ->
                Log.d("postReceipt/", response.toString())
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
        val jsonObj = mapOf(
            "username" to FridgeID.id.toString(), //change
        )
        val request = JsonObjectRequest(
            Request.Method.POST, // maybe change this
            serverUrl + "getReceipts/", JSONObject(jsonObj),
            { response ->
                Log.d("getReceipts", response.toString())
                receipts.clear()
                val arrayTutorialType = object : TypeToken<Array<Array<String>>>() {}.type
                var itemsReceived: Array<Any> =
                    Gson().fromJson(response["receipts"].toString(), arrayTutorialType)
                itemsReceived.forEachIndexed { idx, tut ->
                    val tutArray = tut as Array<String>
                    tutArray.forEachIndexed { index, tut_actual ->
                        if (index.toInt() == 1) {
                            receipts.add(tut_actual.toDouble().toInt())
                        }
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
            queue = Volley.newRequestQueue(context)
        }
        queue.add(request)
    }

}