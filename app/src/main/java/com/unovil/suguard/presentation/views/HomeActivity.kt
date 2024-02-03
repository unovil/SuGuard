package com.unovil.suguard.presentation.views

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.unovil.suguard.ui.screens.HomeScreen
import com.unovil.suguard.ui.screens.SettingsScreen
import com.unovil.suguard.ui.theme.SuGuardTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import javax.inject.Inject

data class BottomNavItem(
    val title: String,
    val itemSelected: ImageVector,
    val itemUnselected: ImageVector,
    val screenRoute: String
)
private const val TAG = "HomeActivity"

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    @Inject lateinit var supabaseClient: SupabaseClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "Started HomeActivity, intent: $intent")

        setContent {
            SuGuardTheme {
                val navController = rememberNavController()

                val bottomNavItems = listOf(
                    BottomNavItem("Home", Icons.Filled.Home, Icons.Outlined.Home, HomeScreen.route),
                    BottomNavItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings, SettingsScreen.route)
                )

                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavBar(
                                navController = navController,
                                bottomNavItems = bottomNavItems
                            )
                        }
                    ) { padding ->

                        NavHost(
                            navController = navController,
                            startDestination = HomeScreen.route,
                            modifier = Modifier.padding(padding),
                            // enterTransition = { fadeIn(tween(150)) },
                            // exitTransition = { fadeOut(tween(150)) }
                        ) {
                            composable(HomeScreen.route) {
                                Log.i(TAG, "Started Home Screen")
                                HomeScreen(navController = navController)
                            }
                            composable(SettingsScreen.route) {
                                SettingsScreen(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    bottomNavItems: List<BottomNavItem>
) {

    NavigationBar {
        val currentRoute = currentRoute(navController)

        bottomNavItems.forEach {bottomNavItem ->
            NavigationBarItem(
                selected = currentRoute == bottomNavItem.screenRoute,
                onClick = {
                    if (currentRoute != bottomNavItem.screenRoute)
                        navController.navigate(bottomNavItem.screenRoute)
                },
                alwaysShowLabel = false,
                label = { Text(bottomNavItem.title) },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == bottomNavItem.screenRoute) {
                            bottomNavItem.itemSelected
                        } else bottomNavItem.itemUnselected,
                        contentDescription = bottomNavItem.title
                    )
                }
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}