// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/MainActivity.kt

package com.example.jourie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.jourie.navigation.*
import com.example.jourie.ui.theme.Gray400
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                enableEdgeToEdge()
                setContent { JourieTheme { JourieRoot() } }
        }
}

data class BottomNavItem(val label: String, val route: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourieRoot() {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        // Cek apakah user sudah login untuk menentukan start destination
        val isLoggedIn = remember { 
                FirebaseAuth.getInstance().currentUser != null 
        }
        val startDestination = if (isLoggedIn) "main_graph" else "auth_graph"

        // Logic Navbar: Hanya muncul di 5 halaman utama, SAMA SEPERTI FAB
        // Kita gunakan list rute yang diizinkan untuk navbar
        val mainRoutes =
                listOf(
                        Routes.DASHBOARD,
                        Routes.ACHIEVEMENTS,
                        Routes.STREAK,
                        Routes.HISTORY,
                        Routes.PROFILE
                )

        val currentRoute = currentDestination?.route

        // Navbar hanya muncul jika rute saat ini ada di daftar mainRoutes
        // Kita perlu handle potential query params atau argument di route,
        // tapi usually 'route' property includes them.
        // For simpler exact matching check:
        val shouldShowBottomBar =
                mainRoutes.any { route -> currentRoute?.startsWith(route) == true }

        // FAB logic sama dengan navbar logic untuk kasus ini
        val shouldShowFAB = shouldShowBottomBar

        // LAYOUT BARU: Menggunakan Box untuk menumpuk elemen (Overlay)
        // 1. Layer Paling Bawah: Scaffold & NavHost (Konten Utama) - Mengisi penuh layer
        // 2. Layer Atas: Navbar & FAB (Melayang di atas konten)
        Box(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                        containerColor =
                                Color.Transparent, // Pastikan background scaffold transparan
                        contentWindowInsets = WindowInsets(0, 0, 0, 0)
                ) { innerPadding ->
                        NavHost(
                                navController = navController,
                                startDestination = startDestination,
                                modifier =
                                        Modifier.fillMaxSize()
                                                .padding(top = innerPadding.calculateTopPadding())
                                // PENTING: Tidak ada bottom padding agar konten tembus ke bawah
                                // navbar
                                ) {
                                authNavGraph(navController)
                                mainNavGraph(navController)
                        }
                }

                // Layer Atas: Navbar (Melayang di Bawah Tengah)
                if (shouldShowBottomBar) {
                        FloatingBottomNavigationBar(
                                navController = navController,
                                modifier = Modifier.align(Alignment.BottomCenter)
                        )
                }

                // Layer Atas: FAB (Melayang di Kanan Bawah)
                if (shouldShowBottomBar && shouldShowFAB) {
                        AddJournalFAB(
                                onClick = { navController.navigate(Routes.ADD_JOURNAL) },
                                modifier =
                                        Modifier.align(Alignment.BottomEnd)
                                                .padding(
                                                        end = 16.dp,
                                                        bottom = 96.dp
                                                ) // Naikkan posisi jadi 96.dp agar ada jarak
                        )
                }
        }
}

@Composable
fun FloatingBottomNavigationBar(
        navController: NavController,
        modifier: Modifier = Modifier // Terima modifier untuk positioning
) {
        val navItems =
                listOf(
                        BottomNavItem("Home", Routes.DASHBOARD, Icons.Default.Home),
                        BottomNavItem("Achieve", Routes.ACHIEVEMENTS, Icons.Default.Star),
                        BottomNavItem("Streak", Routes.STREAK, Icons.Default.LocalFireDepartment),
                        BottomNavItem("History", Routes.HISTORY, Icons.Default.History),
                        BottomNavItem("Profile", Routes.PROFILE, Icons.Default.Person)
                )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Surface(
                modifier =
                        modifier // Gunakan modifier dari parent Box
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .height(70.dp)
                                .shadow(
                                        elevation = 8.dp,
                                        shape = RoundedCornerShape(24.dp),
                                        clip = false
                                ),
                shape = RoundedCornerShape(24.dp),
                color = White
        ) {
                Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        navItems.forEach { item ->
                                // PERBAIKAN: Gunakan startsWith agar rute dengan query params
                                // (seperti history?...)
                                // tetap match
                                val isSelected =
                                        currentDestination?.hierarchy?.any { destination ->
                                                destination.route?.startsWith(item.route) == true
                                        } == true

                                NavigationBarItem(
                                        selected = isSelected,
                                        onClick = {
                                                // Strategi: Home/Dashboard clear back stack untuk fresh start
                                                // Tab lain restore state untuk better UX
                                                if (item.route == Routes.DASHBOARD) {
                                                        navController.navigate(item.route) {
                                                                popUpTo(navController.graph.findStartDestination().id) {
                                                                        inclusive = false
                                                                }
                                                                launchSingleTop = true
                                                                // Tidak ada restoreState untuk Dashboard
                                                        }
                                                } else {
                                                        navController.navigate(item.route) {
                                                                popUpTo(
                                                                        navController.graph
                                                                                .findStartDestination()
                                                                                .id
                                                                ) { saveState = true }
                                                                launchSingleTop = true
                                                                restoreState = true
                                                        }
                                                }
                                        },
                                        icon = {
                                                Icon(
                                                        imageVector = item.icon,
                                                        contentDescription = item.label,
                                                        modifier = Modifier.size(24.dp)
                                                )
                                        },
                                        label = { Text(text = item.label, fontSize = 12.sp) },
                                        colors =
                                                NavigationBarItemDefaults.colors(
                                                        selectedIconColor = Purple400,
                                                        selectedTextColor = Purple400,
                                                        unselectedIconColor = Gray400,
                                                        unselectedTextColor = Gray400,
                                                        indicatorColor = Color.Transparent
                                                )
                                )
                        }
                }
        }
}

@Composable
fun AddJournalFAB(
        onClick: () -> Unit,
        modifier: Modifier = Modifier // Terima modifier untuk positioning
) {
        var isRotated by remember { mutableStateOf(false) }

        val rotation by
                animateFloatAsState(
                        targetValue = if (isRotated) 90f else 0f,
                        animationSpec = tween(durationMillis = 300),
                        label = "FAB Rotation"
                )

        FloatingActionButton(
                onClick = {
                        isRotated = !isRotated
                        onClick()
                },
                modifier =
                        modifier // Gunakan modifier dari parent Box
                                .size(60.dp)
                                .shadow(12.dp, RoundedCornerShape(20.dp))
                                .background(
                                        brush =
                                                Brush.horizontalGradient(
                                                        listOf(Purple400, Purple500)
                                                ),
                                        shape = RoundedCornerShape(20.dp)
                                )
                                .rotate(rotation),
                containerColor = Color.Transparent,
                shape = RoundedCornerShape(20.dp),
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
        ) {
                Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Journal",
                        tint = White,
                        modifier = Modifier.size(28.dp)
                )
        }
}
