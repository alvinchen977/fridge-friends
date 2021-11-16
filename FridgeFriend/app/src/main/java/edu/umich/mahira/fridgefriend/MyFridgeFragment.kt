package edu.umich.mahira.fridgefriend

import androidx.fragment.app.Fragment
import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_my_fridge.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import android.os.Build
import android.os.Handler


val items = arrayListOf<Item?>() //use this for the items

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
//                doCrop(cropIntent)
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
                //updateList(items)
                //MainActivity().setCurrentFragment(this, "myFridgeFragment")
                //update list view after call to api
                //TODO make it so it calls this function immediatly after API call
                Handler().postDelayed({
                    updateList(items)
                    //(activity as MainActivity?)?.setCurrentFrag(this, "myFridgeFragment")
                }, 5000)
            } else {
                Log.d("TakePicture", "failed")
            }

        }
        // Food picture
        view.postGrocery.setOnClickListener {
            imageUri = mediaStoreAlloc("image/jpeg")
            forTakePicture.launch(imageUri)
        }
    }

    fun updateList(newList: ArrayList<Item?>) {
        itemListAdapter.notifyDataSetChanged()
        Log.d("UpdateList", "yes")
    }
}
