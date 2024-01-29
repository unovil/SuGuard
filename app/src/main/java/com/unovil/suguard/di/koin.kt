package com.unovil.suguard.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(androidContext: Application, additionalConfiguration: KoinApplication.() -> Unit = {}) {
    startKoin {
        modules(supabaseModule, viewModelModule)
        androidContext(androidContext)
        additionalConfiguration()
    }
}