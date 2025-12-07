//// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/auth/components/PrimaryAuthButton.kt
//
//package com.example.jourie.presentation.auth.components
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import com.example.jourie.ui.theme.PrimaryPurple
//
//@Composable
//fun PrimaryAuthButton(
//    text: String,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    isLoading: Boolean = false,
//    // DIPERBAIKI: Tambahkan parameter 'enabled' dengan nilai default true
//    enabled: Boolean = true
//) {
//    Button(
//        // DIPERBAIKI: Logika 'enabled' dipindahkan ke dalam 'onClick'
//        onClick = { if (enabled && !isLoading) onClick() },
//        modifier = modifier
//            .fillMaxWidth()
//            .height(50.dp),
//        shape = RoundedCornerShape(10.dp),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = PrimaryPurple,
//            contentColor = Color.White,
//            // DIPERBAIKI: Atur warna saat tombol tidak aktif (disabled)
//            disabledContainerColor = PrimaryPurple.copy(alpha = 0.5f),
//            disabledContentColor = Color.White.copy(alpha = 0.7f)
//        ),
//        // DIPERBAIKI: Gunakan parameter 'enabled' yang baru kita buat
//        enabled = enabled
//    ) {
//        if (isLoading) {
//            CircularProgressIndicator(
//                modifier = Modifier.size(24.dp),
//                color = Color.White,
//                strokeWidth = 2.dp
//            )
//        } else {
//            Text(text = text, fontWeight = FontWeight.Bold)
//        }
//    }
//}

// File: .../presentation/auth/components/PrimaryAuthButton.kt
package com.example.jourie.presentation.auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator // DITAMBAHKAN
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // DITAMBAHKAN
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- DEFINISI FUNGSI DIPERBAIKI UNTUK MENERIMA isLoading ---
@Composable
fun PrimaryAuthButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false, // <-- PARAMETER BARU DITAMBAHKAN
    enabled: Boolean = true // <-- Parameter tambahan agar bisa di-disable saat loading
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
        // Tombol akan non-aktif saat sedang loading
        enabled = enabled && !isLoading
    ) {
        if (isLoading) {
            // Jika sedang loading, tampilkan spinner
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            // Jika tidak loading, tampilkan ikon dan teks
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontSize = 16.sp)
        }
    }
}
