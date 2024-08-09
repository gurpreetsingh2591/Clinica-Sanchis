package com.eurosalud.pdp_cs.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.eurosalud.pdp_cs.utils.SharedPreferencesUtils

class App : Application() {
    private var currentApplication: com.eurosalud.pdp_cs.application.App? = null
    override fun onCreate() {
        super.onCreate()
        currentApplication = this
        SharedPreferencesUtils.init(applicationContext)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

}