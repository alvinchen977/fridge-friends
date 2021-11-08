package edu.umich.mahira.fridgefriend

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import edu.umich.mahira.fridgefriend.databinding.ActivityFridgeBinding
import java.io.ByteArrayOutputStream
import java.io.File

val items = arrayListOf<Item?>() //use this to the items

class FridgeActivity : AppCompatActivity() {
    private lateinit var view: ActivityFridgeBinding
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

        return contentResolver.insert(
            if (mediaType.contains("video"))
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            else
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityFridgeBinding.inflate(layoutInflater)
        view.root.setBackgroundColor(Color.parseColor("#E0E0E0"))
        setContentView(view.root)

        itemListAdapter = GroceryListAdapter(this, items)
        view.GroceryListView.setAdapter(itemListAdapter)

        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            results.forEach {
                if (!it.value) {
                    toast("${it.key} access denied")
                    finish()
                }
            }
        }.launch(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE))

        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            toast("Device has no camera!")
            return
        }

        // Take the food picture
        val forTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
//                doCrop(cropIntent)
                if (imageUri != null) {
                    val uriPathHelper = URIPathHelper()
                    val filePath = imageUri?.let { uriPathHelper.getPath(this, it) }
                    val base64Image = filePath?.let { convertToBase64(it) }
                    val image = GroceryItem(image = base64Image)
                    GroceryItemStore.postGrocery(applicationContext, image) {
                        val intent =
                            Intent(applicationContext, DisplayScannedItemActivity::class.java)
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
    }
}