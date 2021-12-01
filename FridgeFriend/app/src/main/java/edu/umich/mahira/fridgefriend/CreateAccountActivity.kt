package edu.umich.mahira.fridgefriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        var Login: Button = findViewById(R.id.Login)

        var Create: Button = findViewById(R.id.create)

        Login.setOnClickListener {
            val intent =
                Intent(applicationContext, SignInActivity::class.java)
            startActivity(intent)
        }

        Create.setOnClickListener {
            val username: TextView = findViewById(R.id.usernameCreate)
            val password: TextView = findViewById(R.id.passwordCreate)
            val user: User = User(username = username.text.toString(),
            password = password.text.toString())
            FridgeID.createAccount(applicationContext, user){
                if (it == ""){
                    Toast.makeText(applicationContext, "username is incorrect", Toast.LENGTH_LONG)
                }
                else{
                    FridgeID.id = it
                    finish()
                }
            }
        }

    }
}