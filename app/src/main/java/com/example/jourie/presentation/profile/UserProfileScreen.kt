package com.example.jourie.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jourie.navigation.Routes
import com.example.jourie.presentation.profile.components.*
import com.example.jourie.ui.theme.Gray400
import com.example.jourie.ui.theme.JourieTheme

@Composable
fun UserProfileScreen(navController: NavController, viewModel: UserProfileViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    var isDarkMode by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    // Refresh profile saat screen resumed (setelah kembali dari edit)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.refreshProfile()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Observe logout state dan navigate ke login
    LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut) {
            viewModel.onLogoutHandled()
            navController.navigate("auth_graph") {
                popUpTo(0) { 
                    inclusive = true
                    saveState = false
                }
                launchSingleTop = true
                restoreState = false
            }
        }
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            // Gradient Header dengan avatar
            ProfileHeaderGradient(
                    name = state.name,
                    memberSince = "Member since Dec 2024",
                    onCameraClick = {
                        // TODO: Implement camera/gallery picker
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Personal Detail Card
            UserDetailsCard(
                    name = state.name,
                    email = state.email,
                    phone = state.phone,
                    dob = state.dob,
                    onEditClick = { navController.navigate(Routes.EDIT_PROFILE) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Menu Section
            ProfileMenuSection(
                    onSettingsClick = {
                        // TODO: Navigate to settings
                    },
                    onHelpClick = {
                        // TODO: Navigate to help & support
                    },
                    onPrivacyClick = {
                        // TODO: Navigate to privacy policy
                    },
                    onAboutClick = {
                        // TODO: Navigate to about app
                    },
                    isDarkMode = isDarkMode,
                    onDarkModeToggle = { isDarkMode = it },
                    onLogoutClick = { viewModel.logout() },
                    onDeleteAccountClick = {
                        // TODO: Show confirmation dialog
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // App Version Footer
            Text(
                    text = "MoodTracker v1.0.0",
                    fontSize = 13.sp,
                    color = Gray400,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 100.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UserProfileScreenPreview() {
    JourieTheme { UserProfileScreen(navController = rememberNavController()) }
}
