package edu.umich.mahira.fridgefriend

import androidx.fragment.app.Fragment
import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.fragment_my_fridge.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import android.view.inputmethod.InputMethodManager
import edu.umich.mahira.fridgefriend.FridgeItemStore.items
import kotlinx.android.synthetic.main.fragment_my_fridge.*


class MyFridgeFragment:Fragment(R.layout.fragment_my_fridge) {

    private lateinit var itemListAdapter: GroceryListAdapter
    private var imageUri: Uri? = null
    fun convertToBase64(filePath : String): String? {
        val imageFile = File(filePath!!)
        val bm = BitmapFactory.decodeFile(imageFile.toString())
        val bOut = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut)
        return Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT)
    }

    private fun mediaStoreAlloc(mediaType: String): Uri? {
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.MIME_TYPE, mediaType)
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

        return activity?.contentResolver?.insert(
            if (mediaType.contains("video"))
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            else
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values)
    }
    //                update list view after call to api
    var mainHandler = Handler(Looper.getMainLooper())
    private var updateTextTask = object : Runnable {
        override fun run() {
            if (activity?.applicationContext != null) {
                FridgeItemStore.getItems(activity?.applicationContext!!) {
                    updateList()
                    mainHandler.postDelayed(this, 5000)
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FridgeItemStore.getItems(activity?.applicationContext!!){
        }

        itemListAdapter = GroceryListAdapter(requireActivity(), items)
        view.GroceryListView.adapter = itemListAdapter

        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            results.forEach {
                if (!it.value) {
                    activity?.toast("${it.key} access denied")
                    activity?.finish()
                }
            }
        }.launch(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE))

        if (!activity?.packageManager?.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)!!) {
            activity?.toast("Device has no camera!")
        }

        // Take the food picture
        val forTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                if (imageUri != null) {
                    val uriPathHelper = URIPathHelper()
                    val filePath = imageUri?.let { uriPathHelper.getPath(requireActivity(), it) }
                    val base64Image = filePath?.let { convertToBase64(it) }
                    val image = GroceryItem(image = base64Image)
                    GroceryItemStore.postGrocery(activity?.applicationContext!!, image) {
                        val intent =
                            Intent(
                                activity?.applicationContext,
                                DisplayScannedItemActivity::class.java
                            )
                        intent.putExtra("displayText", it)
                        intent.putExtra("imagePath", filePath)
                        startActivity(intent, null)
                        Log.d("returned", it)
                    }
                }
            } else {
                Log.d("TakePicture", "failed")
            }

        }
        // Food picture
        view.postGrocery.setOnClickListener {
            imageUri = mediaStoreAlloc("image/jpeg")
            forTakePicture.launch(imageUri)
        }

        // Find the button which will start editing process.
        val originalKeyListener = input.keyListener;
        input.keyListener = null;
        view.input.setOnClickListener(View.OnClickListener {
            input.keyListener = originalKeyListener;
            // Focus the field.
            input.requestFocus()
            // Show soft keyboard for the user to enter the value.
            val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
        })

        // We also want to disable editing when the user exits the field.
        // This will make the button the only non-programmatic way of editing it.
        input.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            // If it loses focus...
            if (!hasFocus) {
                // Hide soft keyboard.
                val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(input.windowToken, 0)
                // Make it non-editable again.
                input.keyListener = null
            }
        }

        view.add.setOnClickListener(View.OnClickListener {
            if(view.input.text.toString() != ""){
                val image = Item(name = view.input.text.toString(), quantity = 1)
                FridgeItemStore.postItem(activity?.applicationContext!!, image) {
                    updateList()
                    view.input.text.clear()
                    // We also want to disable editing when the user exits the field.
                    // This will make the button the only non-programmatic way of editing it.
                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.add.windowToken, 0)
                }
            }
        })

        mainHandler = Handler(Looper.getMainLooper())

    }
    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateTextTask)
    }

    private fun updateList() {
        itemListAdapter.notifyDataSetChanged()
        Log.d("UpdateList", "yes")
    }
}
