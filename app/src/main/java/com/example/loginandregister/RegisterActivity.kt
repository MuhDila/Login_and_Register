package com.example.loginandregister

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    // Declaration variable
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Properties initialization variable
        editTextEmail = findViewById(R.id.emailRegister)
        editTextPassword = findViewById(R.id.passwordRegister)

        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Setting translucent status bar and navigator Bar
//        val w = window
//        w.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )

        // Setting color status bar and navigator bar
//        window.statusBarColor = resources.getColor(R.color.red, this.theme);
//        window.navigationBarColor = getResources().getColor(R.color.red, this.theme);
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


        // Variable that is converted to a string value
        val emailText: String = editTextEmail.text.toString()
        val passwordText: String = editTextPassword.text.toString()

        // Condition to check if values are empty
        if (emailText.isEmpty() || passwordText.isEmpty()) {
            if (emailText.isEmpty()) {
                editTextEmail.setError("Data harus diisi")
                editTextEmail.requestFocus()
            }
            if (passwordText.isEmpty()) {
                editTextPassword.setError("Data harus diisi")
                editTextPassword.requestFocus()
            }
            return
        }

    }
}