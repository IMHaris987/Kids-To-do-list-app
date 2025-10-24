package com.example.kidstodolist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

// ✅ Base activity that ensures correct language (locale) is applied to all activities
open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        // Load and attach the saved locale before any UI is displayed
        val context = LocaleHelper.loadLocale(newBase)
        super.attachBaseContext(context)
    }
}
