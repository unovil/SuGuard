package com.unovil.suguard.di

import com.unovil.suguard.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseKey = BuildConfig.SUPABASE_PUBLIC_KEY,
            supabaseUrl = BuildConfig.SUPABASE_URL
        ) {
            install(Postgrest)
            install(Auth)
            install(ComposeAuth) {
                googleNativeLogin(BuildConfig.WEB_CLIENT_ID)
            }
        }
    }
}