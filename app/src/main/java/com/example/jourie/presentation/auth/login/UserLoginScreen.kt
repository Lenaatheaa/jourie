////package com.example.jourie.presentation.auth.login
////
////import androidx.compose.foundation.clickable
////import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.rememberScrollState
////import androidx.compose.foundation.text.ClickableText
////import androidx.compose.foundation.text.KeyboardActions
////import androidx.compose.foundation.text.KeyboardOptions
////import androidx.compose.foundation.verticalScroll
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.*
////import androidx.compose.material3.*
////import androidx.compose.runtime.*
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.platform.LocalFocusManager
////import androidx.compose.ui.text.*
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.text.input.*
////import androidx.compose.ui.text.style.TextAlign
////import androidx.compose.ui.unit.dp
////import androidx.compose.ui.unit.sp
////import androidx.lifecycle.viewmodel.compose.viewModel
////import com.example.jourie.presentation.auth.components.*
////import com.example.jourie.ui.theme.PrimaryPurple
////
////@Composable
////fun UserLoginScreen(
////    onLoginSuccess: () -> Unit,
////    onNavigateToRegister: () -> Unit,
////    viewModel: UserLoginViewModel = viewModel()
////) {
////    val state by viewModel.state.collectAsState()
////    val focusManager = LocalFocusManager.current
////
////    Column(
////        modifier = Modifier
////            .fillMaxSize()
////            .padding(horizontal = 24.dp)
////            .verticalScroll(rememberScrollState())
////    ) {
////        AuthScreenHeader(title = "Selamat Datang Kembali!", onBackClick = { /*TODO: handle back press*/ })
////
////        Spacer(modifier = Modifier.height(32.dp))
////
////        // Input Fields
////        CustomAuthTextField(
////            label = "Email",
////            value = state.email,
////            onValueChange = viewModel::onEmailChange,
////            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
////            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
////        )
////
////        Spacer(modifier = Modifier.height(16.dp))
////
////        CustomAuthTextField(
////            label = "Kata Sandi",
////            value = state.password,
////            onValueChange = viewModel::onPasswordChange,
////            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
////            trailingIcon = {
////                IconButton(onClick = viewModel::onPasswordVisibilityToggle) {
////                    Icon(
////                        if (state.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
////                        contentDescription = "Toggle Password Visibility"
////                    )
////                }
////            },
////            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
////            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
////            keyboardActions = KeyboardActions(onDone = {
////                focusManager.clearFocus()
////                viewModel.login(onLoginSuccess)
////            })
////        )
////
////        Text(
////            text = "Lupa kata sandi?",
////            color = Color.Gray,
////            fontSize = 12.sp,
////            textAlign = TextAlign.End,
////            modifier = Modifier
////                .fillMaxWidth()
////                .padding(top = 8.dp)
////                .clickable { /*TODO: handle forgot password*/ }
////        )
////
////        Spacer(modifier = Modifier.height(24.dp))
////
////        // Tombol Masuk
////        PrimaryAuthButton(
////            text = "Masuk",
////            onClick = {
////                focusManager.clearFocus()
////                viewModel.login(onLoginSuccess)
////            },
////            isLoading = state.isLoading
////        )
////
////        Spacer(modifier = Modifier.height(32.dp))
////
////        // Footer
////        val annotatedString = buildAnnotatedString {
////            withStyle(style = SpanStyle(color = Color.Gray)) {
////                append("Belum punya akun? ")
////            }
////            withStyle(style = SpanStyle(color = PrimaryPurple, fontWeight = FontWeight.Bold)) {
////                pushStringAnnotation(tag = "REGISTER", annotation = "REGISTER")
////                append("Buat akun")
////                pop()
////            }
////        }
////
////        ClickableText(
////            text = annotatedString,
////            onClick = { offset ->
////                annotatedString.getStringAnnotations(tag = "REGISTER", start = offset, end = offset)
////                    .firstOrNull()?.let {
////                        onNavigateToRegister()
////                    }
////            },
////            modifier = Modifier.align(Alignment.CenterHorizontally)
////        )
////
////        Spacer(modifier = Modifier.height(24.dp))
////        Divider()
////        Spacer(modifier = Modifier.height(24.dp))
////
////        SocialMediaLogins()
////
////        Spacer(modifier = Modifier.height(24.dp))
////    }
////}
//
//// File: .../presentation/auth/login/UserLoginScreen.kt
//package com.example.jourie.presentation.auth.login
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.text.ClickableText
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.text.*
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.text.withStyle
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.jourie.presentation.auth.components.*
//import com.example.jourie.ui.theme.JourieTheme
//import com.example.jourie.ui.theme.PrimaryPurple
//
//// ---- TIDAK ADA LAGI KODE DUPLIKAT DI SINI ----
//
//// --- DEFINISI FUNGSI DIPERBAIKI UNTUK MENERIMA PARAMETER NAVIGASI ---
//@Composable
//fun UserLoginScreen(
//    onLoginSuccess: () -> Unit,
//    onNavigateToRegister: () -> Unit,
//    viewModel: UserLoginViewModel = viewModel()
//) {
//    val state by viewModel.state.collectAsState()
//    val focusManager = LocalFocusManager.current
//
//    // Efek untuk menangani keberhasilan login
//    LaunchedEffect(key1 = state.loginSuccess) {
//        if (state.loginSuccess) {
//            onLoginSuccess() // Panggil navigasi jika login berhasil
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 24.dp)
//            .verticalScroll(rememberScrollState())
//    ) {
//        AuthScreenHeader(title = "Masuk Akun", onBackClick = { /* Di halaman login, tombol kembali mungkin tidak ada aksi */ })
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Input Fields
//        CustomAuthTextField(
//            label = "Email",
//            value = state.email,
//            onValueChange = viewModel::onEmailChange,
//            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        CustomAuthTextField(
//            label = "Kata Sandi",
//            value = state.password,
//            onValueChange = viewModel::onPasswordChange,
//            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
//            trailingIcon = {
//                IconButton(onClick = viewModel::onPasswordVisibilityToggle) {
//                    Icon(
//                        if (state.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
//                        contentDescription = "Toggle Password Visibility"
//                    )
//                }
//            },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
//            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            keyboardActions = KeyboardActions(onDone = {
//                focusManager.clearFocus()
//                viewModel.login()
//            })
//        )
//
//        state.error?.let {
//            Text(
//                text = it,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Tombol Login
//        PrimaryAuthButton(
//            text = "Masuk",
//            onClick = {
//                focusManager.clearFocus()
//                viewModel.login()
//            },
//            isLoading = state.isLoading
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//        Text("ATAU", modifier = Modifier.align(Alignment.CenterHorizontally), color = Color.Gray)
//        Spacer(modifier = Modifier.height(24.dp))
//
//        SocialMediaLogins(
//            onAppleClick = { /* TODO: Aksi login Apple */ },
//            onGoogleClick = { /* TODO: Aksi login Google */ }
//        )
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        // Footer
//        val annotatedString = buildAnnotatedString {
//            withStyle(style = SpanStyle(color = Color.Gray)) {
//                append("Belum punya akun? ")
//            }
//            withStyle(style = SpanStyle(color = PrimaryPurple, fontWeight = FontWeight.Bold)) {
//                pushStringAnnotation(tag = "REGISTER", annotation = "REGISTER")
//                append("Buat akun")
//                pop()
//            }
//        }
//
//        ClickableText(
//            text = annotatedString,
//            onClick = { offset ->
//                annotatedString.getStringAnnotations(tag = "REGISTER", start = offset, end = offset)
//                    .firstOrNull()?.let { onNavigateToRegister() } // Panggil aksi onNavigateToRegister
//            },
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(bottom = 24.dp)
//        )
//    }
//}
//
//@Preview(showSystemUi = true)
//@Composable
//private fun UserLoginScreenPreview() {
//    JourieTheme {
//        UserLoginScreen({}, {}) // Sediakan lambda kosong untuk preview
//    }
//}


// File: .../presentation/auth/login/UserLoginScreen.kt
package com.example.jourie.presentation.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourie.presentation.auth.components.*
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.TextDark

// ---- KODE LENGKAP DAN BERSIH UNTUK LOGIN SCREEN ----

@Composable
fun UserLoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: UserLoginViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = state.loginSuccess) {
        if (state.loginSuccess) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        // --- HEADER BARU ---
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign In",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "For privacy and security.",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
        // ------------------

        Spacer(modifier = Modifier.height(48.dp))

        // --- INPUT FIELDS ---
        CustomAuthTextField(
            label = "Email address",
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomAuthTextField(
            label = "Password",
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            trailingIcon = {
                IconButton(onClick = viewModel::onPasswordVisibilityToggle) {
                    Icon(
                        if (state.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                viewModel.login()
            })
        )

        state.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // --- TOMBOL FORGOT PASSWORD ---
        TextButton(
            onClick = { /* TODO: Aksi lupa password */ },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)
        ) {
            Text("Forgot password?")
        }
        // ---------------------------

        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Login Utama
        PrimaryAuthButton(
            text = "Continue with Email",
            onClick = {
                focusManager.clearFocus()
                viewModel.login()
            },
            isLoading = state.isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text("OR", modifier = Modifier.align(Alignment.CenterHorizontally), color = Color.Gray)
        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Social Media
        SocialMediaLogins(
            onAppleClick = { /* TODO: Aksi login Apple */ },
            onGoogleClick = { /* TODO: Aksi login Google */ }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- FOOTER BARU ---
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("First time? ", color = Color.Gray)
            ClickableText(
                text = AnnotatedString("Create Account"),
                onClick = { onNavigateToRegister() },
                style = TextStyle(
                    color = PrimaryPurple,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            )
        }
        // --------------------
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UserLoginScreenPreview() {
    JourieTheme {
        UserLoginScreen({}, {})
    }
}
