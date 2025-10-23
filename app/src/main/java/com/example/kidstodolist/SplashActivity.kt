package com.example.kidstodolist

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<View>(R.id.logoContainer)

        // Fade In animation
        logo.animate()
            .alpha(1f)              // go from 0 → 1
            .setDuration(1000)      // 1 second
            .withEndAction {
                // After fade in completes, wait a moment then fade out
                logo.animate()
                    .alpha(0f)      // fade back to 0
                    .setDuration(800) // 0.8 second fade out
                    .setStartDelay(800) // visible for 0.8 seconds before fading out
                    .withEndAction {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
            }
    }
}
