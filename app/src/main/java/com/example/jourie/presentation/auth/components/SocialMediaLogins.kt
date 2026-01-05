// File: .../presentation/auth/components/SocialMediaLogins.kt
package com.example.jourie.presentation.auth.components

// --- IMPORT YANG DIPERLUKAN ---
import androidx.compose.foundation.BorderStroke // <-- PASTIKAN IMPORT INI ADA
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.jourie.R
import com.example.jourie.ui.theme.TextDark

@Composable
fun SocialMediaLogins(
    onAppleClick: () -> Unit,
    onGoogleClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SocialLoginButton(
            text = "Continue with Apple",
            iconRes = R.drawable.apple, // Pastikan file ini ada
            onClick = onAppleClick
        )
        SocialLoginButton(
            text = "Continue with Google",
            iconRes = R.drawable.google1, // Pastikan file ini ada
            onClick = onGoogleClick
        )
    }
}

@Composable
private fun SocialLoginButton(
    text: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextDark),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "$text Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = TextDark,
            fontWeight = FontWeight.SemiBold
        )
    }
}
