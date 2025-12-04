package com.example.jourie.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourie.presentation.profile.components.*
import com.example.jourie.ui.theme.IconGray
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.White

// Layar utama dengan nama unik
@Composable
fun UserProfileScreen(
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
                       // .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 24.dp), // Padding bawah agar tidak tertutup tombol
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        UserDetailsCard(name = state.name, email = state.email, phone = state.phone, dob = state.dob)
                        UserActionMenu(onLogoutClick = { viewModel.logout() })
                        EditProfileCtaButton(onClick = {})
                    }
                }
            }
}

