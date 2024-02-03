package com.unovil.suguard.di

import com.unovil.suguard.presentation.viewmodels.SettingsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.jan.supabase.SupabaseClient

@Module
@InstallIn(ViewModelComponent::class)
object SettingsViewModelModule {

    @Provides
    @ViewModelScoped
    fun providesSettingsViewModel(supabaseClient: SupabaseClient): SettingsViewModel {
        return SettingsViewModel(supabaseClient)
    }
}
