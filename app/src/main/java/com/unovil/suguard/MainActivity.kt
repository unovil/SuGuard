package com.unovil.suguard

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.unovil.suguard.presentation.viewmodels.AuthSharedViewModel
import com.unovil.suguard.ui.screens.LoginScreen
import com.unovil.suguard.ui.theme.SuGuardTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var supabaseClient: SupabaseClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "Started MainActivity, intent: $intent")

        setContent {
            SuGuardTheme {
                val authSharedViewModel: AuthSharedViewModel = hiltViewModel()
                val navController = rememberNavController()
                val navState = navController.currentBackStackEntryAsState()
                Log.i(TAG, "navState destination: ${navState.value?.destination?.route}")

                authSharedViewModel.handleSignInResult(
                    context = LocalContext.current,
                    intent = intent,
                    sessionStatus = supabaseClient.auth.sessionStatus
                )

                NavHost(navController = navController, startDestination = "auth") {
                    navigation(route = "auth", startDestination = "login") {
                        composable(route = LoginScreen.route) {
                            Log.i(TAG, "Started Login Screen")

                            LoginScreen(
                                navController = navController,
                                supabaseClient = supabaseClient,
                                authSharedViewModel = authSharedViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}