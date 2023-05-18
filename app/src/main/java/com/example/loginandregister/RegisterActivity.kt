package com.example.loginandregister

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View.*
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    // Declaration variable
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var buttonSignIn: Button

    // Declare an instance of FirebaseAuth
    private lateinit var auth: FirebaseAuth

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Properties initialization variable
        editTextName = findViewById(R.id.nameRegister)
        editTextEmail = findViewById(R.id.emailRegister)
        editTextPassword = findViewById(R.id.passwordRegister)
        buttonRegister = findViewById(R.id.buttonRegister)
        buttonSignIn = findViewById(R.id.button_signin_register)

        // Setting when button register (Sign Up) is clicked
        buttonRegister.setOnClickListener {
            signUpWithEmailAndPassword()
        }

        // Setting when button Sign Up is clicked
        buttonSignIn.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

//        // Setting translucent status bar and navigator Bar
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Setting color status bar and navigator bar
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }

    private fun signUpWithEmailAndPassword() {
        // Variable that is converted to a string value
        val nameText: String = editTextName.getText().toString()
        val emailText: String = editTextEmail.getText().toString()
        val passwordText: String = editTextPassword.getText().toString()

        // Condition to check if values are empty
        if (emailText.isEmpty()) {
            editTextEmail.error = "Data must be entered"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            editTextEmail.error = "Invalid email address"
        }
        if (passwordText.isEmpty()) {
            editTextPassword.error = "Data must be entered"
        }
        if (passwordText.length < 8) {
            editTextPassword.error = "Password minimum 8 character"
        }
        else {
            auth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(this, "Sign Up With Email : Success", Toast.LENGTH_SHORT).show()
                        val i = Intent(this, LoginActivity::class.java)
                        startActivity(i)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Sign Up With Email : Success", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

}