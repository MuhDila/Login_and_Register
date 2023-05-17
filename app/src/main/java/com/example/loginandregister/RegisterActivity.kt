package com.example.loginandregister

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.*
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    // Declaration variable
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button
    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Properties initialization variable
        editTextName = findViewById(R.id.nameRegister)
        editTextEmail = findViewById(R.id.emailRegister)
        editTextPassword = findViewById(R.id.passwordRegister)
        buttonRegister = findViewById(R.id.buttonRegister)

        // Setting translucent status bar and navigator Bar
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Setting color status bar and navigator bar
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        // Setting when button register (Continue) clicked
        buttonRegister.setOnClickListener {
            // Variable that is converted to a string value
            val emailName: String = editTextName.getText().toString()
            val emailText: String = editTextEmail.getText().toString()
            val passwordText: String = editTextPassword.getText().toString()

            // Condition to check if values are empty
            if (emailName == "" || emailText == "" || passwordText == "") {
                editTextName.error = "Data harus diisi"
                editTextEmail.error = "Data harus diisi"
                editTextPassword.error = "Data harus diisi"
            } else {
                Toast.makeText(this, "Good", Toast.LENGTH_SHORT).show()
            }
        }

    }
}