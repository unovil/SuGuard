package com.unovil.suguard.presentation.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.unovil.suguard.presentation.views.HomeActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.handleDeeplinks
import io.github.jan.supabase.gotrue.parseFragmentAndImportSession
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AuthSharedViewModel"

@HiltViewModel
class AuthSharedViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient,
    @ApplicationContext val applicationContext: Context
) : ViewModel() {
    private val coroutineScope = viewModelScope
    private val loginAlert = MutableStateFlow<String?>(null)
    private val _isSignedIn = MutableStateFlow(false)
    private val _sessionStatus = supabaseClient.auth.sessionStatus
    val sessionStatus = _sessionStatus.value

    companion object {
        val signedInTimes = MutableStateFlow(0)
    }

    fun handleSignInResult(
        navController: NavHostController,
        nativeSignInResult: NativeSignInResult,
        context: Context = applicationContext
    ) {
        Log.i(TAG, "Value of NativeSignInResult is $nativeSignInResult")
        Log.i(TAG, "AuthSharedViewModel was run ${signedInTimes.value} times, now from native")
        signedInTimes.value += 1

        MainScope().launch {
            when (nativeSignInResult) {
                is NativeSignInResult.Success -> {
                    Log.i(TAG, "Sign in success")
                    _isSignedIn.value = true
                    navController.popBackStack("auth", inclusive = true)
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                }
                is NativeSignInResult.Error -> {
                    Log.i(TAG, "Error message: ${nativeSignInResult.message}")
                    /*coroutineScope.launch (Dispatchers.Main) {
                        val exception = kotlin.runCatching {
                            supabaseClient.auth.signInWith(Google)
                        }.exceptionOrNull()

                        if (exception != null) {
                            Log.e(TAG, "Error after manual sign in!" +
                                    " ${exception.message}")
                        }
                    }*/
                }
                else -> {
                    Log.i(TAG, "Sign in error")
                    loginAlert.value = "There was an error while logging in. Please try again."
                }
            }
        }

    }

    fun handleSignInResult(
        intent: Intent,
        sessionStatus: StateFlow<SessionStatus>,
        context: Context = applicationContext
    ) {
        Log.i(TAG, "AuthSharedViewModel was run ${signedInTimes.value} times, now from intent")
        signedInTimes.value += 1

        supabaseClient.handleDeeplinks(intent)
        if (sessionStatus.value is SessionStatus.Authenticated) {
            val intentToAct = Intent(context, HomeActivity::class.java)
            context.startActivity(intentToAct)
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
}