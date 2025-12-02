// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/MainActivity.kt

package com.example.jourie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.jourie.navigation.* // Pastikan ini mengimpor semua yang dibutuhkan
import com.example.jourie.ui.theme.IconGray
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JourieTheme {
                JourieRoot()
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourieRoot() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // --- LOGIKA YANG DIPERBAIKI TOTAL ---
    // Tentukan rute grafik (grup) mana yang sedang aktif.
    // "auth_graph" untuk login/register, "main_graph" untuk aplikasi utama.
    val currentGraphRoute = currentDestination?.parent?.route

    // Bottom Bar hanya akan muncul jika kita berada di dalam "main_graph"
    val shouldShowBottomBar = currentGraphRoute == "main_graph"

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                AppBottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        // NavHost utama yang menjadi router
        NavHost(
            navController = navController,
            startDestination = "auth_graph", // Mulai dari grafik autentikasi
            modifier = Modifier.padding(innerPadding)
        ) {
            authNavGraph(navController) // Daftarkan grafik autentikasi
            mainNavGraph(navController) // Daftarkan grafik utama
        }
    }
}

@Composable
fun AppBottomNavigationBar(navController: NavController) {
    val navItems = listOf(
        BottomNavItem("Home", Routes.DASHBOARD, Icons.Default.Home),
        BottomNavItem("Achievements", Routes.ACHIEVEMENTS, Icons.Default.Star),
        BottomNavItem("Streak", Routes.STREAK, Icons.Default.LocalFireDepartment),
        BottomNavItem("History", Routes.HISTORY, Icons.Default.History),
        BottomNavItem("Profile", Routes.PROFILE, Icons.Default.Person)
    )

    NavigationBar(containerColor = White) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryPurple,
                    selectedTextColor = PrimaryPurple,
                    unselectedIconColor = IconGray,
                    unselectedTextColor = IconGray,
                    indicatorColor = White
                )
            )
        }
    }
}
