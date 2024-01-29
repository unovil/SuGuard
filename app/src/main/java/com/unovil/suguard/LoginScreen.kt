package com.unovil.suguard

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
import androidx.navigation.NavController
import com.unovil.suguard.ui.theme.SuGuardTheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.ui.ProviderButtonContent
import io.github.jan.supabase.gotrue.providers.Google
import org.koin.java.KoinJavaComponent.inject

object LoginScreen : Screen("login")

val supabaseClient : SupabaseClient by inject(SupabaseClient::class.java)
val viewModel : AuthSharedViewModel by inject(AuthSharedViewModel::class.java)

@OptIn(SupabaseExperimental::class)
@Composable
fun LoginScreen(
    navController: NavController
) {

    SuGuardTheme {
        val authState = supabaseClient.composeAuth.rememberSignInWithGoogle(
            onResult = {
                if (it == NativeSignInResult.Success) {
                    navController.navigate("home")
                }
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