package com.example.kidstodolist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val context = LocaleHelper.loadLocale(newBase)
        super.attachBaseContext(context)
    }
}
