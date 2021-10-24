package edu.umich.mahira.fridgefriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    fun startPost(view: View?) = startActivity(Intent(this, PostActivity::class.java))
    fun viewFridge(view: View?) = startActivity(Intent(this, FridgeActivity::class.java))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}