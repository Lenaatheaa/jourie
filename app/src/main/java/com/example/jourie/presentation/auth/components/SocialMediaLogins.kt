//package com.example.jourie.presentation.auth.components
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.jourie.R // Ganti dengan R dari proyek Anda
//import com.example.jourie.ui.theme.TextDark
//
//// --- 2. SocialMediaLogins ---
//@Composable
//fun SocialMediaLogins() {
//    Column(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("atau masuk dengan", color = Color.Gray)
//        Spacer(modifier = Modifier.height(16.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
//        ) {
//            SocialButton(
//                iconId = R.drawable.ic_launcher_background, // Ganti dengan ikon Google Anda
//                onClick = {}
//            )
//            SocialButton(
//                iconId = R.drawable.ic_launcher_background, // Ganti dengan ikon Apple/Facebook Anda
//                onClick = {}
//            )
//        }
//    }
//}
//
//@Composable
//private fun SocialButton(iconId: Int, onClick: () -> Unit) {
//    OutlinedButton(
//        onClick = onClick,
//        modifier = Modifier.size(60.dp),
//        shape = MaterialTheme.shapes.medium,
//        border = BorderStroke(1.dp, Color.LightGray),
//        contentPadding = PaddingValues(0.dp)
//    ) {
//        Icon(
//            painter = painterResource(id = iconId),
//            contentDescription = "Social Login",
//            modifier = Modifier.size(24.dp),
//            tint = Color.Unspecified
//        )
//    }
//}
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
        // --- DIPERBAIKI: Menggunakan BorderStroke() secara langsung ---
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
        // -----------------------------------------------------------
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
