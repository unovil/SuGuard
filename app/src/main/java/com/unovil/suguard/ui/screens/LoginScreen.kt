package com.unovil.suguard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.unovil.suguard.presentation.viewmodels.AuthSharedViewModel
import com.unovil.suguard.ui.theme.SuGuardTheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.ui.ProviderButtonContent
import io.github.jan.supabase.gotrue.providers.Google

data object LoginScreen : Screen("login")

@OptIn(SupabaseExperimental::class)
@Composable
fun LoginScreen(
    navController: NavController,
    authSharedViewModel: AuthSharedViewModel = hiltViewModel(),
    supabaseClient: SupabaseClient
) {

    SuGuardTheme {
        val authState = supabaseClient.composeAuth.rememberSignInWithGoogle(
            onResult = {
                result -> authSharedViewModel.handleSignInResult(navController, result)
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