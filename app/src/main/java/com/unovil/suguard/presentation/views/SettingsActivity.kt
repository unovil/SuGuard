package com.unovil.suguard.presentation.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import com.unovil.suguard.ui.theme.SuGuardTheme

@OptIn(ExperimentalMaterial3Api::class)
class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuGuardTheme {
                Text("Hello")
            }
        }
    }
}