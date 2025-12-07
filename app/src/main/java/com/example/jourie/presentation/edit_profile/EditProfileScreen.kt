//// File: .../presentation/edit_profile/EditProfileScreen.kt
//package com.example.jourie.presentation.edit_profile
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.jourie.ui.theme.TextDark
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun EditProfileScreen(
//    onNavigateBack: () -> Unit,
//    viewModel: EditProfileViewModel = viewModel()
//) {
//    val state by viewModel.state.collectAsStateWithLifecycle()
//
//    // Efek untuk navigasi kembali setelah update berhasil
//    LaunchedEffect(state.isUpdated) {
//        if (state.isUpdated) {
//            onNavigateBack()
//            viewModel.onUpdateHandled() // Reset state agar tidak kembali terus-menerus
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "Personal Detail",
//                        fontWeight = FontWeight.Bold,
//                        color = TextDark,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = onNavigateBack) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back",
//                            tint = TextDark
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(horizontal = 24.dp)
//                .verticalScroll(rememberScrollState()),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(16.dp))
//            EditProfileField(
//                label = "Full Name",
//                value = state.fullName,
//                onValueChange = viewModel::onFullNameChange
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            EditProfileField(
//                label = "Phone Number",
//                value = state.phoneNumber,
//                onValueChange = viewModel::onPhoneNumberChange
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            EditProfileField(
//                label = "Email",
//                value = state.email,
//                onValueChange = viewModel::onEmailChange
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            EditProfileField(
//                label = "Date Of Birth",
//                value = state.dob,
//                onValueChange = viewModel::onDobChange
//            )
//            Spacer(modifier = Modifier.height(32.dp))
//            Button(
//                onClick = viewModel::onUpdateProfile,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp),
//                shape = RoundedCornerShape(12.dp)
//            ) {
//                Text(text = "Update", fontSize = 16.sp)
//            }
//        }
//    }
//}
//
//@Composable
//private fun EditProfileField(
//    label: String,
//    value: String,
//    onValueChange: (String) -> Unit
//) {
//    Column(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.Start
//    ) {
//        Text(text = label, fontWeight = FontWeight.SemiBold, color = TextDark)
//        Spacer(modifier = Modifier.height(8.dp))
//        OutlinedTextField(
//            value = value,
//            onValueChange = onValueChange,
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp),
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = MaterialTheme.colorScheme.primary,
//                unfocusedBorderColor = Color.LightGray
//            )
//        )
//    }
//}
