package com.unovil.suguard.presentation.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.unovil.suguard.ui.theme.SuGuardTheme

data class BottomNavItem(
    val title: String,
    val itemSelected: ImageVector,
    val itemUnselected: ImageVector
)

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuGuardTheme {
                val bottomNavItems = listOf(
                    BottomNavItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
                    BottomNavItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
                )

                var selectedNavItemIndex: Int by rememberSaveable { mutableIntStateOf(0) }

                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavBar(
                                bottomNavItems,
                                selectedNavItemIndex
                            ) { selectedNavItemIndex = it }
                        },
                        content = {
                            Text(text = "Hello world!")
                            Modifier.padding(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    bottomNavItems: List<BottomNavItem>,
    selectedNavItemIndex: Int,
    onNavItemSelected: (Int) -> Unit) {

    NavigationBar {
        bottomNavItems.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                selected = selectedNavItemIndex == index,
                onClick = {
                    onNavItemSelected(index)
                    /* TODO: add navcontroller prop to navitem */
                },
                label = { Text(bottomNavItem.title) },
                icon = {
                    Icon(
                        imageVector = if (index == selectedNavItemIndex) {
                            bottomNavItem.itemSelected
                        } else bottomNavItem.itemUnselected,
                        contentDescription = bottomNavItem.title
                    )
                })
        }
    }
}