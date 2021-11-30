package edu.umich.mahira.fridgefriend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

// activity for choosing own categories
class SetCatActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_cat)
        val editCat1 = findViewById<EditText>(R.id.edit_cat1)
        val editCat2 = findViewById<EditText>(R.id.edit_cat2)
        val editCat3 = findViewById<EditText>(R.id.edit_cat3)
        val editCat4 = findViewById<EditText>(R.id.edit_cat4)
        val editCat5 = findViewById<EditText>(R.id.edit_cat5)

        val button = findViewById<Button>(R.id.button_save)
        /*button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editCat1.text)) {
                replyIntent.putExtra(EXTRA_REPLY1, "Produce") // make it so these are just what was in place previously
            } else {
                replyIntent.putExtra(EXTRA_REPLY1, editCat1.text.toString())
            }
            if (TextUtils.isEmpty(editCat2.text)) {
                replyIntent.putExtra(EXTRA_REPLY2, "Meat")
            } else {
                replyIntent.putExtra(EXTRA_REPLY2, editCat2.text.toString())
            }
            if (TextUtils.isEmpty(editCat3.text)) {
                replyIntent.putExtra(EXTRA_REPLY3, "Grain")
            } else {
                replyIntent.putExtra(EXTRA_REPLY3, editCat3.text.toString())
            }
            if (TextUtils.isEmpty(editCat4.text)) {
                replyIntent.putExtra(EXTRA_REPLY4, "Snacks")
            } else {
                replyIntent.putExtra(EXTRA_REPLY4, editCat4.text.toString())
            }
            if (TextUtils.isEmpty(editCat5.text)) {
                replyIntent.putExtra(EXTRA_REPLY5, "Candy")
            } else {
                replyIntent.putExtra(EXTRA_REPLY5, editCat5.text.toString())
            }

            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }*/
    }

    companion object {
        /*const val EXTRA_REPLY1 = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY1"
        const val EXTRA_REPLY2 = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY2"
        const val EXTRA_REPLY3 = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY3"
        const val EXTRA_REPLY4 = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY4"
        const val EXTRA_REPLY5 = "edu.umich.mahira.fridgefriend.shoplistsql.REPLY5"*/
    }
}