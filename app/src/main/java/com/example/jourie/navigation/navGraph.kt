// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/navigation/NavGraph.kt
package com.example.jourie.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jourie.presentation.achievements.MilestonesScreen
import com.example.jourie.presentation.dashboard.MainDashboardScreen
import com.example.jourie.presentation.edit_profile.EditProfileScreen
import com.example.jourie.presentation.history.JournalHistoryScreen
import com.example.jourie.presentation.journal.add.AddNewJournalScreen
import com.example.jourie.presentation.journal.analysis.JournalAnalysisScreen
import com.example.jourie.presentation.profile.UserProfileScreen
import com.example.jourie.presentation.streak.StreakScreen

// ---- TIDAK ADA LAGI KODE DUPLIKAT YANG DIKOMENTARI DI ATAS INI ----

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
    navigation(startDestination = Routes.DASHBOARD, route = "main_graph") {
        composable(route = Routes.DASHBOARD) { MainDashboardScreen(navController = navController) }
        composable(route = Routes.STREAK) { StreakScreen() }
        composable(route = Routes.ACHIEVEMENTS) { MilestonesScreen() }
        composable(
                route = "${Routes.HISTORY}?dateFilter={dateFilter}",
                arguments =
                        listOf(
                                navArgument("dateFilter") {
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = null
                                }
                        )
        ) { backStackEntry ->
            val dateFilter = backStackEntry.arguments?.getString("dateFilter")
            JournalHistoryScreen(navController = navController, initialDateFilter = dateFilter)
        }

        composable(Routes.PROFILE) { UserProfileScreen(navController = navController) }

        composable(Routes.EDIT_PROFILE) {
            EditProfileScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = Routes.ADD_JOURNAL) {
            AddNewJournalScreen(
                    navController = navController,
                    onJournalSubmitted = { journalContent, journalId ->
                        val encodedContent = java.net.URLEncoder.encode(journalContent, "UTF-8")
                        // MENYERTAKAN JOURNAL ID DALAM NAVIGASI
                        navController.navigate(
                                "${Routes.JOURNAL_ANALYSIS}/$encodedContent?journalId=$journalId"
                        ) {
                            // Clear Add Journal from stack - langsung ke Dashboard saat back
                            popUpTo(Routes.ADD_JOURNAL) { inclusive = true }
                        }
                    }
            )
        }

        composable(
                route = "${Routes.JOURNAL_ANALYSIS}/{journalContent}?journalId={journalId}",
                arguments =
                        listOf(
                                navArgument("journalContent") { type = NavType.StringType },
                                navArgument("journalId") {
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = null
                                }
                        )
        ) { backStackEntry ->
            val rawContent = backStackEntry.arguments?.getString("journalContent")
            val decodedContent =
                    rawContent?.let { raw ->
                        try {
                            java.net.URLDecoder.decode(raw, "UTF-8")
                        } catch (e: Exception) {
                            raw
                        }
                    }
                            ?: "No content provided."

            val journalId = backStackEntry.arguments?.getString("journalId")

            JournalAnalysisScreen(
                    navController = navController,
                    journalContent = decodedContent,
                    journalId = journalId
            )
        }

        composable(route = Routes.PET) {
            // Composable untuk layar Pet
        }
    }
}
