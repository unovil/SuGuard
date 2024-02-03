package com.unovil.suguard.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.unovil.suguard.presentation.viewmodels.AuthSharedViewModel
import com.unovil.suguard.ui.theme.SuGuardTheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.ui.ProviderButtonContent
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google

data object LoginScreen : Screen("login")
const val TAG = "LoginScreen"

@OptIn(SupabaseExperimental::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel,
    supabaseClient: SupabaseClient
) {
    val context = LocalContext.current
    val navControllerState = navController.currentBackStackEntryAsState()
    val sessionStatusState = supabaseClient.auth.sessionStatus.collectAsState()

    SuGuardTheme {
        Log.i(TAG, "destination: ${navControllerState.value?.destination?.route}")
        Log.i(TAG, "result: ${sessionStatusState.value}")

        val authState = supabaseClient.composeAuth.rememberSignInWithGoogle(
            onResult = { result ->
                authSharedViewModel.handleSignInResult(navController, result, context)
            }
        )

        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "Login",
                fontSize = typography.headlineLarge.fontSize,
                fontWeight = typography.headlineLarge.fontWeight,
                fontFamily = typography.headlineLarge.fontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(20.dp)
            )

            OutlinedButton(
                onClick = { authState.startFlow() },
                content = { ProviderButtonContent(Google) }
            )
        }
    }
}