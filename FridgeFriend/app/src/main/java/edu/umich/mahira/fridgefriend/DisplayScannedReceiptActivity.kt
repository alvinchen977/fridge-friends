package edu.umich.mahira.fridgefriend

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import receipts

class DisplayScannedReceiptActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_scanned_receipt)
        val imagePath = intent.getStringExtra("imagePath")
        val imageView: ImageView = findViewById<ImageView>(R.id.imageView)
        val bumming = BitmapFactory.decodeFile(imagePath)
        imageView.setImageBitmap(bumming)
        val textView: TextView = findViewById<EditText>(R.id.textView)

        // Find the button which will start editing process.
        val originalKeyListener = textView.keyListener;
        textView.keyListener = null;
        val buttonShowIme = findViewById<ImageButton>(R.id.button)
        buttonShowIme.setOnClickListener(View.OnClickListener {
            textView.keyListener = originalKeyListener;
            // Focus the field.
            textView.requestFocus()
            // Show soft keyboard for the user to enter the value.
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(textView, InputMethodManager.SHOW_IMPLICIT)
        })

        // We also want to disable editing when the user exits the field.
        // This will make the button the only non-programmatic way of editing it.
        // We also want to disable editing when the user exits the field.
        // This will make the button the only non-programmatic way of editing it.
        textView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            // If it loses focus...
            if (!hasFocus) {
                // Hide soft keyboard.
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textView.windowToken, 0)
                // Make it non-editable again.
                textView.keyListener = null
            }
        }

        val buttonAdd = findViewById<ImageButton>(R.id.button2)
        buttonAdd.setOnClickListener(View.OnClickListener {
            receipts.add(textView.text.toString().toInt())

            finish()
        })
    }
}