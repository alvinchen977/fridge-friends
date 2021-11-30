package edu.umich.mahira.fridgefriend

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Context.toast(message: String, short: Boolean = true) {
    Toast.makeText(this, message, if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
}

fun ImageView.display(uri: Uri) {
    setImageURI(uri)
    visibility = View.VISIBLE
}


