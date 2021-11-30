import android.Manifest
import android.content.ContentValues
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
import androidx.fragment.app.Fragment
import edu.umich.mahira.fridgefriend.*
import edu.umich.mahira.fridgefriend.GroceryItemStore.postReceipt
import kotlinx.android.synthetic.main.fragment_savings.view.*
import java.io.ByteArrayOutputStream
import java.io.File

class SavingsFragment:Fragment(R.layout.fragment_savings) {

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

        if (!activity?.packageManager!!.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            activity?.toast("Device has no camera!")
            return
        }
        val forReceiptTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                if (imageUri != null) {
                    val uriPathHelper = URIPathHelper()
                    val filePath = imageUri?.let { uriPathHelper.getPath(requireActivity(), it) }
                    val base64Image = filePath?.let { convertToBase64(it) }
                    val image = ReceiptItem(pdf = base64Image)
                    postReceipt(activity?.applicationContext!!, image)
                    val intent = Intent(activity?.applicationContext!!, DisplayScannedReceiptActivity::class.java)
                    intent.putExtra("imagePath", filePath)
                    startActivity( intent, null)
                }

            } else {
                Log.d("TakePicture", "failed")
            }
        }

        view.receiptButton.setOnClickListener {
            imageUri = mediaStoreAlloc("image/jpeg")
            forReceiptTakePicture.launch(imageUri)
        }
    }



}
