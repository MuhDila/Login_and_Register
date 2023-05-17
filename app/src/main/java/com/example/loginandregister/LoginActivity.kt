package com.example.loginandregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    // Declaration variable
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignUp: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Properties initialization variable
        editTextEmail = findViewById(R.id.emailLogin)
        editTextPassword = findViewById(R.id.passwordLogin)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonSignUp = findViewById(R.id.button_signup_login)

        // Setting when button Login (Sign In) is clicked
        buttonLogin.setOnClickListener {
            // Variable that is converted to a string value
            val emailText: String = editTextEmail.getText().toString()
            val passwordText: String = editTextPassword.getText().toString()

            // Condition to check if values are empty
            if (emailText.isEmpty()) {
                editTextEmail.error = "Data harus diisi"
            }
            if (passwordText.isEmpty()) {
                editTextPassword.error = "Data harus diisi"
            } else {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
        }

        // Setting when button Sign Up is clicked
        buttonSignUp.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        // Setting translucent status bar and navigator Bar
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Setting color status bar and navigator bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR


    }
}