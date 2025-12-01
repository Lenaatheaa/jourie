// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/auth/register/UserRegisterScreen.kt

package com.example.jourie.presentation.auth.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions // <-- DIPERBAIKI: Import yang hilang ditambahkan
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
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourie.presentation.auth.components.*
import com.example.jourie.ui.theme.PrimaryPurple

@Composable
fun UserRegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: UserRegisterViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AuthScreenHeader(title = "Buat Akun!", onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(32.dp))

        // Input Fields
        CustomAuthTextField(
            label = "Nama Lengkap",
            value = state.fullName,
            onValueChange = viewModel::onFullNameChange,
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomAuthTextField(
            label = "Email",
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomAuthTextField(
            label = "Kata Sandi",
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = viewModel::onPasswordVisibilityToggle) {
                    Icon(
                        if (state.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomAuthTextField(
            label = "Konfirmasi Kata Sandi",
            value = state.confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = viewModel::onConfirmPasswordVisibilityToggle) {
                    Icon(
                        if (state.isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Confirm Password Visibility"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            visualTransformation = if (state.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            // Baris ini sekarang tidak akan error karena import sudah benar
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                viewModel.register(onRegisterSuccess)
            })
        )

        state.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Daftar
        PrimaryAuthButton(
            text = "Selanjutnya",
            onClick = {
                focusManager.clearFocus()
                viewModel.register(onRegisterSuccess)
            },
            isLoading = state.isLoading
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Footer
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("Sudah punya akun? ")
            }
            withStyle(style = SpanStyle(color = PrimaryPurple, fontWeight = FontWeight.Bold)) {
                pushStringAnnotation(tag = "LOGIN", annotation = "LOGIN")
                append("Masuk akun")
                pop()
            }
        }
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "LOGIN", start = offset, end = offset)
                    .firstOrNull()?.let {
                        onNavigateToLogin()
                    }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
