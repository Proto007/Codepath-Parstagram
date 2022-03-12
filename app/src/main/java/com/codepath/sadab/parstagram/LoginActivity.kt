package com.codepath.sadab.parstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(ParseUser.getCurrentUser()!=null){
            goToMainActivity()
        }

        findViewById<Button>(R.id.login_button).setOnClickListener(){
            val username=findViewById<EditText>(R.id.et_UserName).text.toString()
            val password=findViewById<EditText>(R.id.et_Password).text.toString()
            loginUser(username,password)
        }

        findViewById<Button>(R.id.signUpBtn).setOnClickListener(){
            val username=findViewById<EditText>(R.id.et_UserName).text.toString()
            val password=findViewById<EditText>(R.id.et_Password).text.toString()
            signUpUser(username,password)
        }
    }
    private fun signUpUser(username: String,password: String){
        val user=ParseUser()

        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground{e->
            if(e==null){
                Toast.makeText(this,"Registration Successful!",Toast.LENGTH_SHORT).show()
                goToMainActivity()
            }else{
                Toast.makeText(this,"Registration Failed",Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({ user, e ->
                if (user != null) {
                    Log.i(TAG, "Successfully logged in user")
                    goToMainActivity()
                } else {
                    e.printStackTrace()
                    Toast.makeText(this,"error loggin in",Toast.LENGTH_SHORT).show()
                }
            })
        )
    }

    private fun goToMainActivity() {
        val intent= Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object{
        const val TAG="LoginActivity"
    }
}