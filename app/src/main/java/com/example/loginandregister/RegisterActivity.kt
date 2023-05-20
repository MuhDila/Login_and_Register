package com.example.loginandregister

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
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
    private lateinit var mDialog: ProgressDialog

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        // Initialize Firebase Auth
        auth = Firebase.auth
        mDialog = ProgressDialog(this)

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
        val nameText: String = editTextName.text.toString()
        val emailText: String = editTextEmail.text.toString()
        val passwordText: String = editTextPassword.text.toString()
        mDialog.setMessage("Processing...")
        mDialog.show()


        if (nameText.isEmpty()) {
            editTextName.error = "Data must be entered"
            return
        }

        if (emailText.isEmpty()) {
            editTextEmail.error = "Data must be entered"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            editTextEmail.error = "Invalid email address"
            return
        }

        if (passwordText.isEmpty()) {
            editTextPassword.error = "Data must be entered"
            return
        }

        if (passwordText.length < 8) {
            editTextPassword.error = "Password minimum 8 character"
            return
        }

        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(emailText)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods

                    if (signInMethods.isNullOrEmpty()) {
                        auth.createUserWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener { createUserTask ->
                                if (createUserTask.isSuccessful) {
                                    Log.d(TAG, "createUserWithEmail: success")
                                    Toast.makeText(this, "Sign Up With Email: Success", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    mDialog.dismiss()
                                } else {
                                    Log.w(TAG, "createUserWithEmail: failure", createUserTask.exception)
                                    Toast.makeText(this, "Sign Up With Email: Failure", Toast.LENGTH_SHORT).show()
                                    mDialog.dismiss()
                                }
                            }
                    } else {
                        Log.w(TAG, "createUserWithEmail: failure", task.exception)
                        Toast.makeText(this, "Sign Up With Email: Failure, Email already exists", Toast.LENGTH_SHORT).show()
                        editTextEmail.error = "Email already exists"
                        mDialog.dismiss()
                    }
                }
            }
    }

}