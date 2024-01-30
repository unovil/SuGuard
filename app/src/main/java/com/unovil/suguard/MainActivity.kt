package com.unovil.suguard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.unovil.suguard.presentation.views.HomeActivity
import com.unovil.suguard.ui.screens.LoginScreen
import com.unovil.suguard.ui.theme.SuGuardTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var supabaseClient: SupabaseClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuGuardTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "auth") {
                    navigation(route = "auth", startDestination = "login") {
                        composable(route = LoginScreen.route) { LoginScreen(navController, supabaseClient = supabaseClient) }
                    }
                    activity(route = "home") {
                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}