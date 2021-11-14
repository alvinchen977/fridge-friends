package edu.umich.mahira.fridgefriend

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView


class DisplayScannedItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_scanned_item)
        val displayText = intent.getStringExtra("displayText")
        val imagePath = intent.getStringExtra("imagePath")
        val textView: TextView = findViewById<EditText>(R.id.textView)
        textView.text = displayText
        val imageView: ImageView = findViewById<ImageView>(R.id.imageView)
        val bumming = BitmapFactory.decodeFile(imagePath)
        imageView.setImageBitmap(bumming)

        // Find the button which will start editing process.
        val originalKeyListener = textView.keyListener;
        textView.keyListener = null;
        val buttonShowIme = findViewById<Button>(R.id.button)
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
        textView.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            // If it loses focus...
            if (!hasFocus) {
                // Hide soft keyboard.
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textView.windowToken, 0)
                // Make it non-editable again.
                textView.keyListener = null
            }
        }

        val buttonAdd = findViewById<Button>(R.id.button2)
        buttonAdd.setOnClickListener(View.OnClickListener {
            var exist = false
            for(i in items){
                if (i != null) {
                    if(i.name == displayText ){
                        exist = true
                        i.quantity = i.quantity?.plus(1)
                        break
                    }
                }
            }
            if(!exist){
                items.add((Item(textView.text.toString(),1)))
            }
            // Refresh here
            MainActivity().replaceFridgeFragment()
            finish()

        })

    }
}