package com.unovil.suguard.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unovil.suguard.core.models.UserInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import javax.inject.Inject

private const val TAG = "SettingsViewModel"

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient
): ViewModel() {
    private val coroutineScope = viewModelScope
    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut = _isLoggedOut.value

    fun retrieveUserInformation(): UserInformation? {
        val currentUser: UserInfo = supabaseClient.auth.currentUserOrNull() ?: return null
        val userMetadata: JsonObject = currentUser.userMetadata ?: return null

        return UserInformation(
            userMetadata["name"].toString(),
            userMetadata["email"].toString(),
            userMetadata["picture"].toString()
        )
    }

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