package com.unovil.suguard.di

import com.unovil.suguard.BuildConfig
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.dsl.module

val supabaseModule = module {
    single {
        createSupabaseClient(
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