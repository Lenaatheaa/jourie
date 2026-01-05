// File: .../presentation/auth/components/PrimaryAuthButton.kt
package com.example.jourie.presentation.auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator 
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color 
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
