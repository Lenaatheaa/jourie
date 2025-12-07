// File: .../presentation/profile/UserProfileScreen.kt
package com.example.jourie.presentation.profile

// --- SEMUA IMPORT YANG DIPERLUKAN ---
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
// --- DIPERBAIKI: Import yang tidak perlu dihapus ---
// import com.example.jourie.presentation.edit_profile.EditProfileScreen
import com.example.jourie.navigation.Routes
import com.example.jourie.presentation.profile.components.*
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.TextDark

@Composable
fun UserProfileScreen(
    navController: NavController,
    viewModel: UserProfileViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // --- EFFECT UNTUK NAVIGASI LOGOUT ---
    LaunchedEffect(key1 = state.isLoggedOut) {
        if (state.isLoggedOut) {
            navController.navigate(Routes.LOGIN) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                launchSingleTop = true
            }
            viewModel.onLogoutHandled()
        }
    }

    // --- DIALOG UNTUK EDIT PROFIL ---
    if (state.showEditProfileDialog) {
        // --- DIPERBAIKI: Memanggil Composable dialog yang baru ---
        EditProfileDialog(
            initialState = state,
            onDismiss = { viewModel.onDismissEditDialog() },
            onUpdate = { name, phone, email, dob ->
                viewModel.onUpdateProfile(name, phone, email, dob)
            }
        )
    }

    // --- UI UTAMA LAYAR PROFIL ---
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
                    onEditClick = { viewModel.onShowEditDialog() }
                )
                UserActionMenu(onLogoutClick = { viewModel.logout() })
                EditProfileCtaButton(onClick = { viewModel.onShowEditDialog() })
            }
        }
    }
}

// =========================================================================
// === COMPOSABLE UNTUK DIALOG EDIT PROFIL (DIDEFINISIKAN DI SINI) ===
// =========================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfileDialog(
    initialState: UserProfileState,
    onDismiss: () -> Unit,
    onUpdate: (name: String, phone: String, email: String, dob: String) -> Unit
) {
    // State lokal di dalam dialog untuk menampung perubahan sebelum disimpan
    var name by remember { mutableStateOf(initialState.name) }
    var phone by remember { mutableStateOf(initialState.phone) }
    var email by remember { mutableStateOf(initialState.email) }
    var dob by remember { mutableStateOf(initialState.dob) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                // Top Bar di dalam Dialog
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 24.dp, top = 8.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextDark
                        )
                    }
                    Text(
                        text = "Personal Detail",
                        fontWeight = FontWeight.Bold,
                        color = TextDark,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Form
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EditProfileField(label = "Full Name", value = name, onValueChange = { name = it })
                    Spacer(modifier = Modifier.height(16.dp))
                    EditProfileField(label = "Phone Number", value = phone, onValueChange = { phone = it })
                    Spacer(modifier = Modifier.height(16.dp))
                    EditProfileField(label = "Email", value = email, onValueChange = { email = it })
                    Spacer(modifier = Modifier.height(16.dp))
                    EditProfileField(label = "Date Of Birth", value = dob, onValueChange = { dob = it })
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { onUpdate(name, phone, email, dob) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Update", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

// Composable untuk TextField di dalam dialog
@Composable
private fun EditProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = label, fontWeight = FontWeight.SemiBold, color = TextDark)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.LightGray
            )
        )
    }
}

// Preview
@Preview(showSystemUi = true)
@Composable
private fun UserProfileScreenPreview() {
    JourieTheme {
        UserProfileScreen(navController = rememberNavController())
    }
}

