@file:Suppress("DEPRECATION")

package com.example.loginandregister

import android.app.ProgressDialog
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
import com.example.loginandregister.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    // Declaration variable
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignUp: Button
    private lateinit var sendName: String

    // Declare an instance of FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var mDialog: ProgressDialog
    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth
        mDialog = ProgressDialog(this)

        // Properties initialization variable
        editTextEmail = findViewById(R.id.emailLogin)
        editTextPassword = findViewById(R.id.passwordLogin)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonSignUp = findViewById(R.id.button_signup_login)

        // Setting when button Login (Sign In) is clicked
        binding.buttonLogin.setOnClickListener {
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
        val emailLog : String = binding.emailLogin.text.toString().replace(".", "_")
        mDialog.setMessage("Processing...")
        mDialog.show()

        if (emailText.isEmpty()) {
            editTextEmail.error = "Data must be entered"
            mDialog.dismiss()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            editTextEmail.error = "Invalid email address"
            mDialog.dismiss()
            return
        }

        if (passwordText.isEmpty()) {
            editTextPassword.error = "Data must be entered"
            mDialog.dismiss()
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
                        mDialog.dismiss()
                    } else {
                        auth.signInWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success")
                                    auth.currentUser
                                    Toast.makeText(this, "Sign In With Email : Success", Toast.LENGTH_SHORT).show()
                                    mDialog.dismiss()
                                    readData(emailLog)
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                                    Toast.makeText(this, "Sign In With Email : Failure. Wrong password", Toast.LENGTH_SHORT).show()
                                    editTextPassword.error = "Password is wrong"
                                    mDialog.dismiss()
                                }
                            }
                    }
                }
            }
    }
    private fun readData(email: String) {
        database = FirebaseDatabase.getInstance().getReference("User")
        database.child(email).get().addOnSuccessListener {
            if (it.exists()){
                val name = it.child("name").value
                binding.emailLogin.text?.clear()
                binding.passwordLogin.text?.clear()
                sendName = name.toString()
                val i = Intent(this, MainActivity::class.java)
                i.putExtra("nameFromLog", sendName)
                startActivity(i)
            }else{
                Toast.makeText(this,"User Doesn't Exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
    }
}