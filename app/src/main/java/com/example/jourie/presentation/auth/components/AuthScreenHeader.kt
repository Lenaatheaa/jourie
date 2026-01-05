// File: .../presentation/auth/components/AuthScreenHeader.kt
package com.example.jourie.presentation.auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.TextDark

// --- DEFINISI FUNGSI DIPERBAIKI ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreenHeader(
    title: String,onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TopAppBar untuk menampung tombol kembali dan judul
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = TextDark
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        modifier = modifier
    )
}




