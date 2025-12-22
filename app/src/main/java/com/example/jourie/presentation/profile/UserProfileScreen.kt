package com.example.jourie.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jourie.navigation.Routes
import com.example.jourie.presentation.profile.components.*
import com.example.jourie.ui.theme.JourieTheme

@Composable
fun UserProfileScreen(
    navController: NavController,
    viewModel: UserProfileViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                UserDetailsCard(
                    name = state.name,
                    email = state.email,
                    phone = state.phone,
                    dob = state.dob,
                    onEditClick = { navController.navigate(Routes.EDIT_PROFILE) }
                )
                UserActionMenu(onLogoutClick = { viewModel.logout() })
                EditProfileCtaButton(onClick = { navController.navigate(Routes.EDIT_PROFILE) })
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UserProfileScreenPreview() {
    JourieTheme {
        UserProfileScreen(navController = rememberNavController())
    }
}

