// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/navigation/NavGraph.kt

package com.example.jourie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jourie.presentation.achievements.MilestonesScreen
import com.example.jourie.presentation.dashboard.MainDashboardScreen
//import com.example.jourie.presentation.edit_profile.EditProfileScreen
import com.example.jourie.presentation.history.JournalHistoryScreen
import com.example.jourie.presentation.journal.add.AddNewJournalScreen
import com.example.jourie.presentation.journal.analysis.JournalAnalysisScreen
import com.example.jourie.presentation.profile.UserProfileScreen
import com.example.jourie.presentation.streak.StreakScreen

object Routes {
    // Rute Autentikasi
    const val LOGIN = "login_screen"
    const val REGISTER = "register_screen"

    // Rute Setelah Login
    const val DASHBOARD = "dashboard_screen"
    const val STREAK = "streak_screen"
    const val ACHIEVEMENTS = "achievements_screen"
    const val HISTORY = "history_screen"
    const val PROFILE = "profile_screen"
    const val ADD_JOURNAL = "add_journal_screen"
    const val PET = "pet_screen"
    const val JOURNAL_ANALYSIS = "journal_analysis_screen"

    const val EDIT_PROFILE = "edit_profile"
}

// Grafik utama setelah login
fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Routes.DASHBOARD,
        route = "main_graph"
    ) {
        composable(route = Routes.DASHBOARD) {
            MainDashboardScreen(navController = navController)
        }
        composable(route = Routes.STREAK) {
            StreakScreen()
        }
        composable(route = Routes.ACHIEVEMENTS) {
            MilestonesScreen()
        }
        composable(route = Routes.HISTORY) {
            JournalHistoryScreen()
        }
        composable(Routes.PROFILE) {
            // UserProfileScreen does not accept a NavController parameter
            UserProfileScreen(navController = navController)
        }
        // Di dalam NavHost


        composable(route = Routes.ADD_JOURNAL) {
            AddNewJournalScreen(
                navController = navController,
                onJournalSubmitted = { journalContent ->
                    val encodedContent = java.net.URLEncoder.encode(journalContent, "UTF-8")
                    navController.navigate("${Routes.JOURNAL_ANALYSIS}/$encodedContent")
                }
            )
        }

        // --- DITAMBAHKAN DAN DIPERBAIKI ---
        composable(
            route = "${Routes.JOURNAL_ANALYSIS}/{journalContent}",
            arguments = listOf(navArgument("journalContent") { type = NavType.StringType })
        ) {
            // Pemanggilan ini sekarang benar. ViewModel di dalam JournalAnalysisScreen
            // akan secara otomatis mengambil argumen 'journalContent'.
            JournalAnalysisScreen(navController = navController)
        }

        composable(route = Routes.PET) {
            // Composable untuk layar Pet
        }
    }
}
