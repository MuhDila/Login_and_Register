package com.example.loginandregister

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button

class IntroductionActivity : AppCompatActivity() {

    private lateinit var buttonGetStarted: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        // Set variable
        buttonGetStarted = findViewById(R.id.buttonGetStarted)

        // Setting translucent status bar and navigator Bar
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Setting color status bar and navigator bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        // Setting when button Get Started clicked
        buttonGetStarted.setOnClickListener {
                val i = Intent(this, RegisterActivity::class.java)
                startActivity(i)
        }
    }
}