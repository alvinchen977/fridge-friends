package edu.umich.mahira.fridgefriend

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        var signIn: Button = findViewById(R.id.SignIn)

        var createAccount: Button = findViewById(R.id.createAccount)



        signIn.setOnClickListener {
            val username: TextView = findViewById(R.id.username)
            val password: TextView = findViewById(R.id.password)
            val user: User = User(username = username.text.toString(),
                password = password.text.toString())
            FridgeID.signIn(applicationContext, user){
                if (it == ""){
                    Toast.makeText(applicationContext, "username is incorrect", Toast.LENGTH_LONG)
                }
                else{
                    FridgeID.id = it
                    finish()
                }
            }
        }

        createAccount.setOnClickListener {
            val intent =
                Intent(applicationContext, CreateAccountActivity::class.java)
            startActivity(intent)
        }

    }
}