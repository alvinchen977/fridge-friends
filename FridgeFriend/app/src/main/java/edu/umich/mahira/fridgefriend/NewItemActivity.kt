package edu.umich.mahira.fridgefriend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

// activity for entering an item
class NewItemActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)
        val editItemView = findViewById<EditText>(R.id.edit_item)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editItemView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val item = editItemView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, item)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY"
    }
}