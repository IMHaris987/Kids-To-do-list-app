package com.example.kidstodolist

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.loadLocale(base))
    }

    override fun onCreate() {
        super.onCreate()
        LocaleHelper.loadLocale(this)
    }
}
