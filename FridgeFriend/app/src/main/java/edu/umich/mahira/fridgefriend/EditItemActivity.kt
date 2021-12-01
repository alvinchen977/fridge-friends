package edu.umich.mahira.fridgefriend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import org.w3c.dom.Text

// activity for entering an item
class EditItemActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val replyIntent = Intent() unused w/ commented out
        //val itemTo = replyIntent.getCharSequenceExtra("itemToChange") unused w/ commented out

        //val changedItem: TextView = findViewById<EditText>(R.id.changed_item)

        setContentView(R.layout.activity_edit_item)
        //setContentView(ShopListAdapter, (R.layout.activity_edit_item, findViewById<EditText>(R.id.changed_item).text -> itemTo))

        //setLocusContext(R.layout.activity_edit_item,current)

        //val changeItem = findViewById<TextView>(R.id.change_item)
        //changeItem.setText(itemTo)
        //changeItem.text = itemTo

        //changeItem.setHint()
        //changeItem.setOnEditorActionListener()
        //changeItem.setExtractedText(replyIntent.getStringExtra("itemToChange"))

        val changedItem = findViewById<EditText>(R.id.changed_item)
        //CharSequence cs = itemTo
        //changedItem.text = itemTo.splitToSequence() for CharSequence
        //changedItem.setText(itemTo as Editable?)
        //changedItem.setText(itemTo)
        //changedItem.text = itemTo as Editable?
        //changedItem.setHint(itemTo)
        //changedItem.hint = itemTo
//        changedItem.addTextChangedListener { charSequence ->
//            changedItem.text = charSequenc.toString()
//
//        }

// cleared bc didn't change the separation
//        val button = findViewById<Button>(R.id.button_save)
//        button.setOnClickListener {
//            if (TextUtils.isEmpty(changedItem.text)) {
//                replyIntent.putExtra(EXTRA_REPLY, itemTo)
//                setResult(Activity.RESULT_OK, replyIntent)
//            } else {
//                val item = changedItem.text.toString()
//                replyIntent.putExtra(EXTRA_REPLY, item)
//                setResult(Activity.RESULT_OK, replyIntent)
//            }
//            finish()
//        }
    }

    companion object {
        //const val EXTRA_REPLY = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY"
        /*const val EXTRA_REPLY1 = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY1"
        const val EXTRA_REPLY2 = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY2"
        const val EXTRA_REPLY3 = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY3"*/
    }
}