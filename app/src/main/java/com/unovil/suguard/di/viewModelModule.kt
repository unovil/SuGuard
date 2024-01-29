package com.unovil.suguard.di

import com.unovil.suguard.AuthSharedViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single {
        AuthSharedViewModel(get())
    }
}
