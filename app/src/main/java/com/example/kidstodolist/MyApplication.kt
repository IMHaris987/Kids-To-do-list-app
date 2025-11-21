package com.example.kidstodolist

import android.app.Application
import android.content.Context
import com.example.kidstodolist.utils.LocaleHelper

// Custom Application class that initializes global settings for the app
// In this case, it's used to handle app-wide locale (language) management
class MyApplication : Application() {

    // Called before onCreate; attaches a base context that respects the user's language preference
    override fun attachBaseContext(base: Context) {
        // Load and apply the saved locale before anything else runs
        super.attachBaseContext(LocaleHelper.loadLocale(base))
    }

    // Called when the app process starts (before any activity or service)
    override fun onCreate() {
        super.onCreate()

        // Ensures that the app's language setting is correctly applied globally
        LocaleHelper.loadLocale(this)
    }
}
