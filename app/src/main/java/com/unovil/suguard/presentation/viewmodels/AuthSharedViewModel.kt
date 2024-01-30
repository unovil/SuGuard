package com.unovil.suguard.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.parseFragmentAndImportSession
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthSharedViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient
) : ViewModel() {
    private val coroutineScope = viewModelScope
    private val loginAlert = MutableStateFlow<String?>(null)
    private val _isSignedIn = MutableStateFlow(false)

    fun handleSignInResult(navController: NavController, result: NativeSignInResult) {
        if (result == NativeSignInResult.Success) {
            _isSignedIn.value = true
            navController.navigate("home") {
                popUpTo("auth") { inclusive = true }
            }
        } else {
            loginAlert.value = "There was an error while logging in. Please try again."
        }
    }

    fun signUp(email: String, password: String) {
        coroutineScope.launch {
            kotlin.runCatching {
                supabaseClient.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
            }.onSuccess {
                loginAlert.value = "Successfully registered! Check your E-Mail to verify your account."
            }.onFailure {
                loginAlert.value = "There was an error while registering: ${it.message}"
            }
        }
    }

    fun login(email: String, password: String) {
        coroutineScope.launch {
            kotlin.runCatching {
                supabaseClient.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
            }.onFailure {
                it.printStackTrace()
                loginAlert.value = "There was an error while logging in. Check your credentials and try again."
            }
        }
    }

    fun loginWithIdToken(idToken: String) {
        coroutineScope.launch {
            kotlin.runCatching {
                supabaseClient.auth.signInWith(IDToken)  {
                    this.idToken = idToken
                    provider = Google
                }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    @OptIn(SupabaseInternal::class)
    fun parseFragment(fragment: String) {
        coroutineScope.launch {
            kotlin.runCatching {
                supabaseClient.auth.parseFragmentAndImportSession(fragment)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun loginWithGoogle() {
        coroutineScope.launch {
            kotlin.runCatching {
                supabaseClient.auth.signInWith(Google)
            }
        }
    }

    fun logout() {
        coroutineScope.launch {
            kotlin.runCatching {
                supabaseClient.auth.signOut()
            }
        }
    }

}