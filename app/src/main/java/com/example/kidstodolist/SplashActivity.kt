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

        // Animate splash logo with fade in and fade out effects
        logo.animate()
            .alpha(1f)              // fade in from transparent
            .setDuration(1000)      // for 1 second
            .withEndAction {
                // After fade-in → delay → fade-out → open MainActivity
                logo.animate()
                    .alpha(0f)      // fade out to transparent
                    .setDuration(800)
                    .setStartDelay(800) // stay visible for 0.8 sec
                    .withEndAction {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish() // close splash so user can't go back to it
                    }
            }
    }
}
