//// File:
// A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/auth/register/UserRegisterScreen.kt
//
// package com.example.jourie.presentation.auth.register
//
// import androidx.compose.foundation.clickable
// import androidx.compose.foundation.layout.*
// import androidx.compose.foundation.rememberScrollState
// import androidx.compose.foundation.text.ClickableText
// import androidx.compose.foundation.text.KeyboardActions // <-- DIPERBAIKI: Import yang hilang
// ditambahkan
// import androidx.compose.foundation.text.KeyboardOptions
// import androidx.compose.foundation.verticalScroll
// import androidx.compose.material.icons.Icons
// import androidx.compose.material.icons.filled.*
// import androidx.compose.material3.*
// import androidx.compose.runtime.*
// import androidx.compose.ui.Alignment
// import androidx.compose.ui.Modifier
// import androidx.compose.ui.graphics.Color
// import androidx.compose.ui.platform.LocalFocusManager
// import androidx.compose.ui.text.*
// import androidx.compose.ui.text.font.FontWeight
// import androidx.compose.ui.text.input.*
// import androidx.compose.ui.unit.dp
// import androidx.lifecycle.viewmodel.compose.viewModel
// import com.example.jourie.presentation.auth.components.*
// import com.example.jourie.ui.theme.PrimaryPurple
//
// @Composable
// fun UserRegisterScreen(
//    onRegisterSuccess: () -> Unit,
//    onNavigateToLogin: () -> Unit,
//    onBackClick: () -> Unit,
//    viewModel: UserRegisterViewModel = viewModel()
// ) {
//    val state by viewModel.state.collectAsState()
//    val focusManager = LocalFocusManager.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 24.dp)
//            .verticalScroll(rememberScrollState())
//    ) {
//        AuthScreenHeader(title = "Buat Akun!", onBackClick = onBackClick)
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Input Fields
//        CustomAuthTextField(
//            label = "Nama Lengkap",
//            value = state.fullName,
//            onValueChange = viewModel::onFullNameChange,
//            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
//            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words,
// imeAction = ImeAction.Next)
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        CustomAuthTextField(
//            label = "Email",
//            value = state.email,
//            onValueChange = viewModel::onEmailChange,
//            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction =
// ImeAction.Next)
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
//                        if (state.isPasswordVisible) Icons.Default.Visibility else
// Icons.Default.VisibilityOff,
//                        contentDescription = "Toggle Password Visibility"
//                    )
//                }
//            },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction =
// ImeAction.Next),
//            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else
// PasswordVisualTransformation()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        CustomAuthTextField(
//            label = "Konfirmasi Kata Sandi",
//            value = state.confirmPassword,
//            onValueChange = viewModel::onConfirmPasswordChange,
//            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
//            trailingIcon = {
//                IconButton(onClick = viewModel::onConfirmPasswordVisibilityToggle) {
//                    Icon(
//                        if (state.isConfirmPasswordVisible) Icons.Default.Visibility else
// Icons.Default.VisibilityOff,
//                        contentDescription = "Toggle Confirm Password Visibility"
//                    )
//                }
//            },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction =
// ImeAction.Done),
//            visualTransformation = if (state.isConfirmPasswordVisible) VisualTransformation.None
// else PasswordVisualTransformation(),
//            // Baris ini sekarang tidak akan error karena import sudah benar
//            keyboardActions = KeyboardActions(onDone = {
//                focusManager.clearFocus()
//                viewModel.register(onRegisterSuccess)
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
//        // Tombol Daftar
//        PrimaryAuthButton(
//            text = "Selanjutnya",
//            onClick = {
//                focusManager.clearFocus()
//                viewModel.register(onRegisterSuccess)
//            },
//            isLoading = state.isLoading
//        )
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Footer
//        val annotatedString = buildAnnotatedString {
//            withStyle(style = SpanStyle(color = Color.Gray)) {
//                append("Sudah punya akun? ")
//            }
//            withStyle(style = SpanStyle(color = PrimaryPurple, fontWeight = FontWeight.Bold)) {
//                pushStringAnnotation(tag = "LOGIN", annotation = "LOGIN")
//                append("Masuk akun")
//                pop()
//            }
//        }
//        ClickableText(
//            text = annotatedString,
//            onClick = { offset ->
//                annotatedString.getStringAnnotations(tag = "LOGIN", start = offset, end = offset)
//                    .firstOrNull()?.let {
//                        onNavigateToLogin()
//                    }
//            },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//    }
// }

package com.example.jourie.presentation.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourie.ui.theme.Gray200
import com.example.jourie.ui.theme.Gray400
import com.example.jourie.ui.theme.Gray50
import com.example.jourie.ui.theme.Gray500
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.Purple50
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

@Composable
fun UserRegisterScreen(
        onRegisterSuccess: () -> Unit,
        onNavigateToLogin: () -> Unit,
        onBackClick: () -> Unit,
        viewModel: UserRegisterViewModel = viewModel()
) {
        val state by viewModel.state.collectAsState()
        val focusManager = LocalFocusManager.current

        LaunchedEffect(key1 = state.registerSuccess) {
                if (state.registerSuccess) {
                        onRegisterSuccess()
                }
        }

        // Scrollable container dengan background abu-abu muda
        Box(modifier = Modifier.fillMaxSize().background(Purple50)) {
                Column(
                        modifier =
                                Modifier.fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Spacer(modifier = Modifier.height(40.dp))

                        // 1. Logo Placeholder
                        Box(
                                modifier =
                                        Modifier.size(80.dp)
                                                .shadow(8.dp, CircleShape)
                                                .background(White, CircleShape),
                                contentAlignment = Alignment.Center
                        ) {
                                Text(
                                        text = "J",
                                        fontSize = 40.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Purple500
                                )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Header Text
                        Text(
                                text = "Create Account",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Gray900
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                                text = "Sign up to start your journey",
                                fontSize = 14.sp,
                                color = Gray500
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // 2. Main Register Card
                        Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(24.dp),
                                colors = CardDefaults.cardColors(containerColor = White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                                Column(modifier = Modifier.padding(24.dp)) {
                                        // Full Name Field
                                        RegisterOutlinedTextField(
                                                value = state.fullName,
                                                onValueChange = viewModel::onFullNameChange,
                                                labelText = "Full Name",
                                                keyboardOptions =
                                                        KeyboardOptions(
                                                                capitalization =
                                                                        KeyboardCapitalization
                                                                                .Words,
                                                                imeAction = ImeAction.Next
                                                        )
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        // Email Field
                                        RegisterOutlinedTextField(
                                                value = state.email,
                                                onValueChange = viewModel::onEmailChange,
                                                labelText = "Email",
                                                keyboardOptions =
                                                        KeyboardOptions(
                                                                keyboardType = KeyboardType.Email,
                                                                imeAction = ImeAction.Next
                                                        )
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        // Password Field
                                        RegisterOutlinedTextField(
                                                value = state.password,
                                                onValueChange = viewModel::onPasswordChange,
                                                labelText = "Password",
                                                trailingIcon = {
                                                        IconButton(
                                                                onClick =
                                                                        viewModel::onPasswordVisibilityToggle
                                                        ) {
                                                                Icon(
                                                                        imageVector =
                                                                                if (state.isPasswordVisible
                                                                                )
                                                                                        Icons.Default
                                                                                                .Visibility
                                                                                else
                                                                                        Icons.Default
                                                                                                .VisibilityOff,
                                                                        contentDescription =
                                                                                "Toggle Password",
                                                                        tint =
                                                                                if (state.isPasswordVisible
                                                                                )
                                                                                        Purple500
                                                                                else Gray400
                                                                )
                                                        }
                                                },
                                                visualTransformation =
                                                        if (state.isPasswordVisible)
                                                                VisualTransformation.None
                                                        else PasswordVisualTransformation(),
                                                keyboardOptions =
                                                        KeyboardOptions(
                                                                keyboardType =
                                                                        KeyboardType.Password,
                                                                imeAction = ImeAction.Next
                                                        )
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        // Confirm Password Field
                                        RegisterOutlinedTextField(
                                                value = state.confirmPassword,
                                                onValueChange = viewModel::onConfirmPasswordChange,
                                                labelText = "Confirm Password",
                                                trailingIcon = {
                                                        IconButton(
                                                                onClick =
                                                                        viewModel::onConfirmPasswordVisibilityToggle
                                                        ) {
                                                                Icon(
                                                                        imageVector =
                                                                                if (state.isConfirmPasswordVisible
                                                                                )
                                                                                        Icons.Default
                                                                                                .Visibility
                                                                                else
                                                                                        Icons.Default
                                                                                                .VisibilityOff,
                                                                        contentDescription =
                                                                                "Toggle Confirm Password",
                                                                        tint =
                                                                                if (state.isConfirmPasswordVisible
                                                                                )
                                                                                        Purple500
                                                                                else Gray400
                                                                )
                                                        }
                                                },
                                                visualTransformation =
                                                        if (state.isConfirmPasswordVisible)
                                                                VisualTransformation.None
                                                        else PasswordVisualTransformation(),
                                                keyboardOptions =
                                                        KeyboardOptions(
                                                                keyboardType =
                                                                        KeyboardType.Password,
                                                                imeAction = ImeAction.Done
                                                        ),
                                                keyboardActions =
                                                        KeyboardActions(
                                                                onDone = {
                                                                        focusManager.clearFocus()
                                                                        viewModel.register()
                                                                }
                                                        )
                                        )

                                        // Error Message
                                        state.error?.let {
                                                Spacer(modifier = Modifier.height(8.dp))
                                                Text(
                                                        text = it,
                                                        color = MaterialTheme.colorScheme.error,
                                                        style = MaterialTheme.typography.bodySmall
                                                )
                                        }

                                        Spacer(modifier = Modifier.height(24.dp))

                                        // Register Button (Gradient)
                                        Button(
                                                onClick = {
                                                        focusManager.clearFocus()
                                                        viewModel.register()
                                                },
                                                modifier =
                                                        Modifier.fillMaxWidth()
                                                                .height(50.dp)
                                                                .shadow(
                                                                        4.dp,
                                                                        RoundedCornerShape(12.dp)
                                                                ),
                                                colors =
                                                        ButtonDefaults.buttonColors(
                                                                containerColor = Color.Transparent
                                                        ),
                                                contentPadding = PaddingValues(),
                                                shape = RoundedCornerShape(12.dp),
                                                enabled = !state.isLoading
                                        ) {
                                                Box(
                                                        modifier =
                                                                Modifier.fillMaxSize()
                                                                        .background(
                                                                                brush =
                                                                                        Brush.horizontalGradient(
                                                                                                colors =
                                                                                                        listOf(
                                                                                                                Purple400,
                                                                                                                Purple500
                                                                                                        )
                                                                                        )
                                                                        ),
                                                        contentAlignment = Alignment.Center
                                                ) {
                                                        if (state.isLoading) {
                                                                CircularProgressIndicator(
                                                                        color = White,
                                                                        modifier =
                                                                                Modifier.size(24.dp)
                                                                )
                                                        } else {
                                                                Text(
                                                                        text = "Sign Up",
                                                                        color = White,
                                                                        fontWeight =
                                                                                FontWeight.Bold,
                                                                        fontSize = 16.sp
                                                                )
                                                        }
                                                }
                                        }

                                        Spacer(modifier = Modifier.height(24.dp))

                                        // OR Divider
                                        Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                        ) {
                                                Divider(
                                                        modifier = Modifier.weight(1f),
                                                        color = Gray200.copy(alpha = 0.5f)
                                                )
                                                Text(
                                                        text = "OR",
                                                        modifier =
                                                                Modifier.padding(
                                                                        horizontal = 16.dp
                                                                ),
                                                        color = Gray400,
                                                        fontSize = 12.sp
                                                )
                                                Divider(
                                                        modifier = Modifier.weight(1f),
                                                        color = Gray200.copy(alpha = 0.5f)
                                                )
                                        }

                                        Spacer(modifier = Modifier.height(24.dp))

                                        // Social Buttons (Consistent with Login)
                                        RegisterSocialButton(
                                                text = "Sign up with Google",
                                                onClick = { /* TODO */},
                                                iconColor = Color.Red,
                                                iconLetter = "G"
                                        )
                                }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Footer
                        Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                        text = "Already have an account? ",
                                        color = Gray500,
                                        fontSize = 14.sp
                                )
                                Text(
                                        text = "Sign In",
                                        color = Purple500,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        textDecoration = TextDecoration.Underline,
                                        modifier = Modifier.clickable { onNavigateToLogin() }
                                )
                        }

                        Spacer(modifier = Modifier.height(32.dp))
                }
        }
}

// --- Helper Components (Modified for Floating Label) ---

@Composable
fun RegisterOutlinedTextField(
        value: String,
        onValueChange: (String) -> Unit,
        labelText: String,
        trailingIcon: @Composable (() -> Unit)? = null,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions.Default
) {
        OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(labelText) }, // Floating Label
                trailingIcon = trailingIcon,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors =
                        OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Purple500,
                                unfocusedBorderColor = Gray200,
                                errorBorderColor = MaterialTheme.colorScheme.error,
                                focusedContainerColor = Gray50,
                                unfocusedContainerColor = Gray50,
                                focusedLabelColor = Purple500,
                                unfocusedLabelColor = Gray400
                        ),
                textStyle = TextStyle(fontSize = 14.sp, color = Gray900)
        )
}

@Composable
fun RegisterSocialButton(text: String, onClick: () -> Unit, iconColor: Color, iconLetter: String) {
        OutlinedButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Gray200),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = White)
        ) {
                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                ) {
                        Text(
                                text = iconLetter,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = iconColor
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                                text = text,
                                color = Gray900,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                        )
                }
        }
}

@Preview(showSystemUi = true)
@Composable
private fun UserRegisterScreenPreview() {
        JourieTheme { UserRegisterScreen({}, {}, {}) }
}
