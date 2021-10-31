package edu.umich.mahira.fridgefriend

import android.Manifest
import android.content.ComponentName
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import edu.umich.mahira.fridgefriend.databinding.ActivityPostBinding
import android.graphics.BitmapFactory

import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.util.Base64
import com.android.volley.RequestQueue

import edu.umich.mahira.fridgefriend.GroceryItemStore.postGrocery
import edu.umich.mahira.fridgefriend.GroceryItemStore.postReceipt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class PostActivity : AppCompatActivity() {

    private lateinit var forCropResult: ActivityResultLauncher<Intent>

    private lateinit var view: ActivityPostBinding

    private val serverUrl = "https://18.118.131.242"
    private lateinit var queue: RequestQueue

    private var imageUri: Uri? = null

    fun getBase64FromPath(path: String?): String? {
        var base64: String? = ""
        val file = File(path)
        val buffer = ByteArray(file.length().toInt() + 100)
        val length: Int = FileInputStream(file).read(buffer)
        base64 = Base64.encodeToString(
            buffer, 0, length,
            Base64.DEFAULT
        )
        return base64
    }
    fun convertToBase64(filePath : String): String? {
        val imageFile = File(filePath!!)
        val bm = BitmapFactory.decodeFile(imageFile.toString())
        val bOut = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut)
        return Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT)
    }
    private fun doCrop(intent: Intent?) {
        intent ?: run {
            imageUri?.let { view.previewImage.display(it) }
            return
        }

        imageUri?.let {
            intent.data = it
            forCropResult.launch(intent)
        }
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

    // Convert to pdf Function
    private fun convertToPdf(pathToPicture: String){
        val name = pathToPicture.substringAfterLast("/").substringBeforeLast(".jpg")
        var directory1 = pathToPicture.substringBeforeLast("/")
        var directory = directory1.substringBeforeLast("/")
        var pdfFile = "$directory/Documents/$name.pdf"

        val bitmap = BitmapFactory.decodeFile(pathToPicture)
        val pdfDocument = PdfDocument()

        val myPageInfo = PageInfo.Builder(960, 1280, 1).create()
        val page = pdfDocument.startPage(myPageInfo)

        val float1 = 0
        val float2 = 0

        page.canvas.drawBitmap(bitmap,float1.toFloat(),float2.toFloat(), null)
        pdfDocument.finishPage(page)

        val myPDFFile = File(pdfFile)

        pdfDocument.writeTo(FileOutputStream(myPDFFile));
        pdfDocument.close()
    }

    private fun initCropIntent(): Intent? {
        // Is there any published Activity on device to do image cropping?
        val intent = Intent("com.android.camera.action.CROP")
        intent.type = "image/*"
        val listofCroppers = packageManager.queryIntentActivities(intent, 0)
        // No image cropping Activity published
        if (listofCroppers.size == 0) {
            toast("Device does not support image cropping")
            return null
        }

        intent.component = ComponentName(
            listofCroppers[0].activityInfo.packageName,
            listofCroppers[0].activityInfo.name)

        // create a square crop box:
        intent.putExtra("outputX", 500)
            .putExtra("outputY", 500)
            .putExtra("aspectX", 1)
            .putExtra("aspectY", 1)
            // enable zoom and crop
            .putExtra("scale", true)
            .putExtra("crop", true)
            .putExtra("return-data", true)

        return intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cropIntent = initCropIntent()
        view = ActivityPostBinding.inflate(layoutInflater)
        setContentView(view.root)

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

        forCropResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.data.let {
                        imageUri?.run {
                            if (!toString().contains("ORIGINAL")) {
                                // delete uncropped photo taken for posting
                                contentResolver.delete(this, null, null)
                            }
                        }
                        imageUri = it
                        imageUri?.let { view.previewImage.display(it) }
                    }
                } else {
                    Log.d("Crop", result.resultCode.toString())
                }
            }

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
                    postGrocery(applicationContext, image){
                        val intent = Intent(applicationContext,DisplayScannedItemActivity::class.java)
                        intent.putExtra("displayText", it)
                        intent.putExtra("imagePath", filePath)
                        startActivity( intent, null)
                        Log.d("returned", it)
                    }

                }
            } else {
                Log.d("TakePicture", "failed")
            }
        }
        // Take the Receipt picture
        val forReceiptTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
//                doCrop(cropIntent)
                // PDF
//                val uriPathHelper = URIPathHelper()
//                val filePath = imageUri?.let { uriPathHelper.getPath(this, it) }
//                convertToPdf(filePath.toString())
//
//                val name = filePath.toString().substringAfterLast("/").substringBeforeLast(".jpg")
//                var directory1 = filePath.toString().substringBeforeLast("/")
//                var directory = directory1.substringBeforeLast("/")
//                var pdfFile = "$directory/Documents/$name.pdf"
//
//                val base64Image = getBase64FromPath(pdfFile)
//                val image = ReceiptItem(pdf = base64Image)
//
//                postReceipt(applicationContext, image)
                if (imageUri != null) {
                    val uriPathHelper = URIPathHelper()
                    val filePath = imageUri?.let { uriPathHelper.getPath(this, it) }
                    val base64Image = filePath?.let { convertToBase64(it) }
                    val image = ReceiptItem(pdf = base64Image)
                    postReceipt(applicationContext, image)
                    val intent = Intent(applicationContext,DisplayScannedReceiptActivity::class.java)
                    intent.putExtra("imagePath", filePath)
                    startActivity( intent, null)
                }

            } else {
                Log.d("TakePicture", "failed")
            }
        }
        // Food picture
        view.addImage.setOnClickListener {
            imageUri = mediaStoreAlloc("image/jpeg")
            forTakePicture.launch(imageUri)
        }
        // Receipt Picture
        view.receiptButton.setOnClickListener {
            imageUri = mediaStoreAlloc("image/jpeg")
            forReceiptTakePicture.launch(imageUri)
        }

    }
}