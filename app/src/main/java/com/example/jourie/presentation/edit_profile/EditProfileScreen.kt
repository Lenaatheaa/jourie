package com.example.jourie.presentation.edit_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourie.presentation.profile.components.ProfileHeaderGradient
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.TextDark
import com.example.jourie.ui.theme.White

@Composable
fun EditProfileScreen(onNavigateBack: () -> Unit, viewModel: EditProfileViewModel = viewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Efek untuk navigasi kembali setelah update berhasil
    LaunchedEffect(state.isUpdated) {
        if (state.isUpdated) {
            onNavigateBack()
            viewModel.onUpdateHandled() // Reset state agar tidak kembali terus-menerus
        }
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        // Header Section dengan Back Button Overlay
        Box(modifier = Modifier.fillMaxWidth()) {
            ProfileHeaderGradient(
                    name = state.fullName.ifBlank { "User" },
                    memberSince = "Edit Profile Info"
            )

            // Back Button Custom di atas Header
            IconButton(
                    onClick = onNavigateBack,
                    modifier =
                            Modifier.padding(top = 48.dp, start = 16.dp)
                                    .align(Alignment.TopStart)
                                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Form Card Section
        Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                        text = "Edit Personal Detail",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                )

                Spacer(modifier = Modifier.height(20.dp))

                EditProfileField(
                        label = "Full Name",
                        value = state.fullName,
                        onValueChange = viewModel::onFullNameChange
                )
                Spacer(modifier = Modifier.height(16.dp))
                EditProfileField(
                        label = "Phone Number",
                        value = state.phoneNumber,
                        onValueChange = viewModel::onPhoneNumberChange
                )
                Spacer(modifier = Modifier.height(16.dp))
                EditProfileField(
                        label = "Email",
                        value = state.email,
                        onValueChange = viewModel::onEmailChange
                )
                Spacer(modifier = Modifier.height(16.dp))
                EditProfileField(
                        label = "Date Of Birth",
                        value = state.dob,
                        onValueChange = viewModel::onDobChange
                )

                // Error Message
                state.error?.let {
                    Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 8.dp),
                            fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Update Button
                Button(
                        onClick = viewModel::onUpdateProfile,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors =
                                ButtonDefaults.buttonColors(
                                        containerColor = PrimaryPurple,
                                        disabledContainerColor = PrimaryPurple.copy(alpha = 0.5f)
                                ),
                        enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = White,
                                strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                                text = "Save Changes",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun EditProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF999999)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors =
                        OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryPurple,
                                unfocusedBorderColor = Color.LightGray,
                                focusedLabelColor = PrimaryPurple,
                                cursorColor = PrimaryPurple
                        ),
                singleLine = true
        )
    }
}
