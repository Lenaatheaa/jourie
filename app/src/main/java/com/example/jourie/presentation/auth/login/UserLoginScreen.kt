package com.example.jourie.presentation.auth.login

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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

        Box(modifier = Modifier.fillMaxSize().background(Purple50)) {
                Column(
                        modifier =
                                Modifier.fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Spacer(modifier = Modifier.height(40.dp))

                        // 1. Logo
                        Box(
                                modifier =
                                        Modifier.size(80.dp)
                                                .shadow(8.dp, CircleShape)
                                                .background(White, CircleShape),
                                contentAlignment = Alignment.Center
                        ) {
                                Image(
                                        painter = painterResource(id = com.example.jourie.R.drawable.jourielogo1),
                                        contentDescription = "Jourie Logo",
                                        modifier = Modifier
                                                .size(50.dp)
                                                .offset(x = (-2).dp, y = (-2).dp)
                                )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Header Text
                        Text(
                                text = "Welcome Back",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Gray900
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                                text = "Sign in to continue your journey",
                                fontSize = 14.sp,
                                color = Gray500
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // 2. Main Login Card
                        Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(24.dp),
                                colors = CardDefaults.cardColors(containerColor = White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                                Column(modifier = Modifier.padding(24.dp)) {

                                        // Email Field (Floating Label)
                                        AuthOutlinedTextField(
                                                value = state.email,
                                                onValueChange = viewModel::onEmailChange,
                                                labelText = "Email",
                                                keyboardOptions =
                                                        KeyboardOptions(
                                                                keyboardType = KeyboardType.Email,
                                                                imeAction = ImeAction.Next
                                                        ),
                                                isError =
                                                        state.error?.contains(
                                                                "email",
                                                                ignoreCase = true
                                                        ) == true
                                        )

                                        Spacer(modifier = Modifier.height(20.dp))

                                        // Password Field (Floating Label)
                                        AuthOutlinedTextField(
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
                                                                imeAction = ImeAction.Done
                                                        ),
                                                keyboardActions =
                                                        KeyboardActions(
                                                                onDone = {
                                                                        focusManager.clearFocus()
                                                                        viewModel.login()
                                                                }
                                                        ),
                                                isError =
                                                        state.error?.contains(
                                                                "password",
                                                                ignoreCase = true
                                                        ) == true ||
                                                                state.error?.contains(
                                                                        "sandi",
                                                                        ignoreCase = true
                                                                ) == true
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

                                        // Forgot Password
                                        Box(
                                                modifier = Modifier.fillMaxWidth(),
                                                contentAlignment = Alignment.CenterEnd
                                        ) {
                                                TextButton(
                                                        onClick = { /* TODO: Forgot Password Action */
                                                        },
                                                        contentPadding = PaddingValues(0.dp)
                                                ) {
                                                        Text(
                                                                text = "Forgot password?",
                                                                color = Purple500,
                                                                fontSize = 12.sp,
                                                                fontWeight = FontWeight.Medium
                                                        )
                                                }
                                        }

                                        Spacer(modifier = Modifier.height(16.dp))

                                        // Login Button (Gradient)
                                        Button(
                                                onClick = {
                                                        focusManager.clearFocus()
                                                        viewModel.login()
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
                                                                        text =
                                                                                "Continue with Email",
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
                                                HorizontalDivider(
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
                                                HorizontalDivider(
                                                        modifier = Modifier.weight(1f),
                                                        color = Gray200.copy(alpha = 0.5f)
                                                )
                                        }

                                        Spacer(modifier = Modifier.height(24.dp))

                                        // Social Buttons
                                        SocialLoginButton(
                                                text = "Continue with Google",
                                                onClick = { /* TODO */},
                                                iconDrawable = com.example.jourie.R.drawable.google1
                                        )

                                        Spacer(modifier = Modifier.height(12.dp))

                                        SocialLoginButton(
                                                text = "Continue with Android",
                                                onClick = { /* TODO */},
                                                iconDrawable = com.example.jourie.R.drawable.androidlogo
                                        )
                                }
                        } // End of Card

                        Spacer(modifier = Modifier.height(32.dp))

                        // Footer
                        Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "First time? ", color = Gray500, fontSize = 14.sp)
                                Text(
                                        text = "Create Account",
                                        color = Purple500,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        textDecoration = TextDecoration.Underline,
                                        modifier = Modifier.clickable { onNavigateToRegister() }
                                )
                        }

                        Spacer(modifier = Modifier.height(32.dp))
                }
        }
}

// --- Helper Components Modified for Floating Label ---

@Composable
fun AuthOutlinedTextField(
        value: String,
        onValueChange: (String) -> Unit,
        labelText: String,
        trailingIcon: @Composable (() -> Unit)? = null,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions.Default,
        isError: Boolean = false
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
                isError = isError,
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
fun SocialLoginButton(text: String, onClick: () -> Unit, iconDrawable: Int?) {
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
                        iconDrawable?.let {
                                Image(
                                        painter = painterResource(id = it),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                        }
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
private fun UserLoginScreenPreview() {
        JourieTheme { UserLoginScreen({}, {}) }
}
