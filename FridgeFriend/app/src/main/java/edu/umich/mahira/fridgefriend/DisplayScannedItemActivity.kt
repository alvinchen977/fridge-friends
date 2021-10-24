package edu.umich.mahira.fridgefriend

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DisplayScannedItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_scanned_item)
        val displayText = intent.getStringExtra("displayText")
        val imagePath = intent.getStringExtra("imagePath")
        val textView: TextView = findViewById<TextView>(R.id.textView)
        textView.text = displayText
        val imageView: ImageView = findViewById<ImageView>(R.id.imageView)
        val bmimg = BitmapFactory.decodeFile(imagePath)
        imageView.setImageBitmap(bmimg)
    }
}