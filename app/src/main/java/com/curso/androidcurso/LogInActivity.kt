package com.curso.androidcurso

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val buttonLogIn: Button = findViewById(R.id.logInButton)
        val userName: TextView = findViewById(R.id.userName)

        buttonLogIn.setOnClickListener {
            if (userName.text.isNotEmpty()) {
                val sharedPref = this.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
                sharedPref.edit().putString(getString(R.string.username_key), userName.text.toString()).apply()
                // go to main activity
                val intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}