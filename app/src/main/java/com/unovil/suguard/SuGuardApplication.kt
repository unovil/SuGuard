package com.unovil.suguard

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SuGuardApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}