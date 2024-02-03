package com.unovil.suguard.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SettingsViewModel"

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient
): ViewModel() {
    private val coroutineScope = viewModelScope
    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut = _isLoggedOut.value

    fun logout() {
        coroutineScope.launch {
            kotlin.runCatching {
                supabaseClient.auth.signOut()
            }
        }

        Log.i(TAG, "Signed out success.")
        _isLoggedOut.value = true
    }
}