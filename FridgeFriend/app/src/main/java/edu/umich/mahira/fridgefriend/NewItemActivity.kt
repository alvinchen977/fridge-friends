package edu.umich.mahira.fridgefriend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

// activity for entering an item
class NewItemActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)
        val editItemView = findViewById<EditText>(R.id.edit_item)
        val editNumView = findViewById<EditText>(R.id.edit_num)
        val editTypeView = findViewById<EditText>(R.id.edit_type)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editItemView.text) || TextUtils.isEmpty(editNumView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } /*else if (TextUtils.isEmpty(editTypeView.text) && TextUtils.isEmpty(edit....)) {
                //val item = editNumView.text.toString() + " " + editItemView.text.toString()
                // nothing now that split item into name amount type

                //Shop(item).iName = editItemView.text.toString()
                //Shop(item).iAmount = Integer.parseInt(editNumView.toString())

                //replyIntent.putExtra(EXTRA_REPLY, item)
                //setResult(Activity.RESULT_OK, replyIntent)
                replyIntent.putExtra(EXTRA_REPLY1, Integer.parseInt(editNumView.toString()))
                replyIntent.putExtra(EXTRA_REPLY2, "")
                replyIntent.putExtra(EXTRA_REPLY3, editItemView.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }*/ else if (TextUtils.isEmpty(editTypeView.text)) {
                val item = editNumView.text.toString() + " " + editItemView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, item)
                // nothing now that split item into name amount type

                //Shop(item).iName = editItemView.text.toString()
                //Shop(item).iAmount = Integer.parseInt(editNumView.toString())

                //replyIntent.putExtra(EXTRA_REPLY, item)
                //setResult(Activity.RESULT_OK, replyIntent)
                //replyIntent.putExtra(EXTRA_REPLY0, "produce") ?cat?
                /*replyIntent.putExtra(EXTRA_REPLY1, Integer.parseInt(editNumView.text.toString())) ?split?
                replyIntent.putExtra(EXTRA_REPLY2, "")
                replyIntent.putExtra(EXTRA_REPLY3, editItemView.text.toString())*/
                setResult(Activity.RESULT_OK, replyIntent)
            } else {
                val item = editNumView.text.toString() + " " + editTypeView.text.toString() + " of " + editItemView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, item)
                // nothing now that split item into name amount type

                //
                /*val tempItem: Shop = Shop(/*"produce", ?cat?*/Integer.parseInt(editNumView.text.toString()), editTypeView.text.toString(), editItemView.text.toString())
                tempItem.iName = editItemView.text.toString() ?split?
                tempItem.iAmount = Integer.parseInt(editNumView.text.toString())
                tempItem.iType = editTypeView.text.toString()

                //replyIntent.putExtra(EXTRA_REPLY, item)
                //replyIntent.putExtra(EXTRA_REPLY0, "produce")
                replyIntent.putExtra(EXTRA_REPLY1, Integer.parseInt(editNumView.text.toString()))
                replyIntent.putExtra(EXTRA_REPLY2, editTypeView.text.toString())
                replyIntent.putExtra(EXTRA_REPLY3, editItemView.text.toString())*/
                setResult(Activity.RESULT_OK, replyIntent)
            }
            Log.d("NewItem", "right before finish")
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY"
        //const val EXTRA_REPLY0 = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY0"
        /*const val EXTRA_REPLY1 = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY1" ?split?
        const val EXTRA_REPLY2 = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY2"
        const val EXTRA_REPLY3 = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY3"*/
    }
}