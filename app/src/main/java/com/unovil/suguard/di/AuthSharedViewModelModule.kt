package com.unovil.suguard.di

import android.content.Context
import com.unovil.suguard.presentation.viewmodels.AuthSharedViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.jan.supabase.SupabaseClient

@Module
@InstallIn(ViewModelComponent::class)
object AuthSharedViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideAuthSharedViewModel(supabaseClient: SupabaseClient, applicationContext: Context): AuthSharedViewModel {
        return AuthSharedViewModel(supabaseClient, applicationContext = applicationContext)
    }
}
