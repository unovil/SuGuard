package com.unovil.suguard

import android.app.Application
import com.unovil.suguard.di.initKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(this@Application)
    }
}