//// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/navigation/AuthNavGraph.kt
//
//package com.example.jourie.navigation
//
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.compose.composable
//import androidx.navigation.navigation
//import com.example.jourie.presentation.auth.login.UserLoginScreen
//import com.example.jourie.presentation.auth.register.UserRegisterScreen // Pastikan import ini benar
//
//// Ini adalah "sub-grafik" untuk autentikasi
//fun NavGraphBuilder.authNavGraph(navController: NavController) {
//    // 'navigation' membungkus beberapa layar menjadi satu grafik bernama "auth_graph"
//    navigation(
//        startDestination = Routes.LOGIN,
//        route = "auth_graph" // Nama untuk grup navigasi ini
//    ) {
//        composable(Routes.LOGIN) {
//            UserLoginScreen(
//                onLoginSuccess = {
//                    navController.navigate("main_graph") {
//                        // Hapus grafik auth dari back stack
//                        popUpTo("auth_graph") { inclusive = true }
//                    }
//                },
//                onNavigateToRegister = {
//                    navController.navigate(Routes.REGISTER)
//                }
//            )
//        }
//        composable(Routes.REGISTER) {
//            // --- DIPERBAIKI: Hapus path Firebase yang salah ---
//            UserRegisterScreen(
//                onRegisterSuccess = {
//                    navController.navigate("main_graph") {
//                        popUpTo("auth_graph") { inclusive = true }
//                    }
//                },
//                onNavigateToLogin = {
//                    navController.popBackStack()
//                },
//                onBackClick = {
//                    navController.popBackStack()
//                }
//            )
//        }
//    }
//}
// File: .../navigation/AuthNavGraph.kt
package com.example.jourie.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jourie.presentation.auth.login.UserLoginScreen
import com.example.jourie.presentation.auth.register.UserRegisterScreen

// --- DIPERBAIKI: Menambahkan import untuk object Routes ---
import com.example.jourie.navigation.Routes
// --------------------------------------------------------

// Ini adalah "sub-grafik" untuk autentikasi
fun NavGraphBuilder.authNavGraph(navController: NavController) {
    // 'navigation' membungkus beberapa layar menjadi satu grafik bernama "auth_graph"
    navigation(
        // Baris ini sekarang tidak akan error karena Routes sudah diimpor
        startDestination = Routes.LOGIN,
        route = "auth_graph" // Nama untuk grup navigasi ini
    ) {
        composable(Routes.LOGIN) {
            UserLoginScreen(
                onLoginSuccess = {
                    navController.navigate("main_graph") {
                        // Hapus grafik auth dari back stack
                        popUpTo("auth_graph") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }
        composable(Routes.REGISTER) {
            UserRegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("main_graph") {
                        popUpTo("auth_graph") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
