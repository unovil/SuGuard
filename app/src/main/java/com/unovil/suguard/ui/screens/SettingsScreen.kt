package com.unovil.suguard.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.unovil.suguard.R
import com.unovil.suguard.presentation.views.BottomNavBar
import com.unovil.suguard.presentation.views.BottomNavItem
import com.unovil.suguard.ui.theme.SuGuardTheme

data object SettingsScreen : Screen("settings")

@Composable
fun SettingsScreen(
    navController: NavHostController,
    // settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    SuGuardTheme {
        Column {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                Column {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(8.dp, Color.Black, CircleShape) // for testing
                            .align(Alignment.CenterHorizontally),
                        contentDescription = "google",
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 27.sp,
                        fontWeight = FontWeight.SemiBold,
                        text = "Uno Villegas (Banoffee)",
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "uno070825@gmail.com",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Divider(color = Color.DarkGray, thickness = 1.dp)

                    Row {
                        Image(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            modifier = Modifier
                                .clip(RectangleShape)
                                .size(20.dp), // for testing

                            contentDescription = "google"
                        )
                        Text(
                            text = "Log out",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }

        val openDialog = remember{ mutableStateOf(false) }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            // settingsViewModel.logout()
                            openDialog.value = false
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                title = {
                    Text("please log out.")
                },
                text = {
                    Text(text = "log outtttt")
                }
            )
        }

        /*Log.i(HomeActivity::class.simpleName, "Log out status? ${settingsViewModel.isLoggedOut}")

        if (settingsViewModel.isLoggedOut) {
            Log.i(HomeActivity::class.simpleName, "to log out.")
            val intent = Intent(LocalContext.current, MainActivity::class.java)
            LocalContext.current.startActivity(intent)
        }*/
    }
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "SettingsScreen"
)
private fun Preview() {
    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Filled.Home, Icons.Outlined.Home, HomeScreen.route),
        BottomNavItem(
            "Settings",
            Icons.Filled.Settings,
            Icons.Outlined.Settings,
            SettingsScreen.route
        )
    )
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = rememberNavController(), bottomNavItems = bottomNavItems)
        }
    ) { padding ->
        Modifier.padding(padding)
        SettingsScreen(navController = rememberNavController())
    }

}
