package com.unovil.suguard.ui.composables

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.ui.ProviderIcon
import io.github.jan.supabase.gotrue.providers.Google

@OptIn(SupabaseExperimental::class)
@Composable
fun SignInGoogleButton(onClickEvent: () -> Unit) {
    OutlinedButton(
        onClick = onClickEvent,// onClickEvent,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.Black,
            containerColor = Color.White
        ),
        content = {
            ProviderIcon(provider = Google, contentDescription = "Google sign in")
            Text(text = "Continue with Google")
        }
    )
}