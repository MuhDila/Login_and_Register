package com.example.loginandregister

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    // Declaration variable
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignUp: Button

    // Declare an instance of FirebaseAuth
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Properties initialization variable
        editTextEmail = findViewById(R.id.emailLogin)
        editTextPassword = findViewById(R.id.passwordLogin)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonSignUp = findViewById(R.id.button_signup_login)

        // Variable that is converted to a string value
        val emailText: String = editTextEmail.getText().toString()
        val passwordText: String = editTextPassword.getText().toString()

        // Setting when button Login (Sign In) is clicked
        buttonLogin.setOnClickListener {
            signInWithEmailAndPassword()
        }

        // Setting when button Sign Up is clicked
        buttonSignUp.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        // Setting translucent status bar and navigator Bar
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Setting color status bar and navigator bar
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }

    private fun signInWithEmailAndPassword() {
        val emailText: String = editTextEmail.text.toString()
        val passwordText: String = editTextPassword.text.toString()

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

        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(emailText)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods

                    if (signInMethods.isNullOrEmpty()) {
                        Log.w(TAG, "createUserWithEmail: failure", task.exception)
                        Toast.makeText(this, "Sign In With Email: Failure. Email doesn't exists", Toast.LENGTH_SHORT).show()
                        editTextEmail.error = "Email doesn't exists"
                    } else {
                        auth.signInWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success")
                                    val user = auth.currentUser
                                    Toast.makeText(this, "Sign In With Email : Success", Toast.LENGTH_SHORT).show()
                                    val i = Intent(this, MainActivity::class.java)
                                    startActivity(i)
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                                    Toast.makeText(this, "Sign In With Email : Failure. Wrong password", Toast.LENGTH_SHORT).show()
                                    editTextPassword.error = "Password is wrong"
                                }
                            }
                    }
                }

            }
    }
}