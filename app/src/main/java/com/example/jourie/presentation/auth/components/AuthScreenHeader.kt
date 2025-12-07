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
//// --- 1. AuthScreenHeader ---
//@Composable
//fun AuthScreenHeader(title: String, onBackClick: () -> Unit) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 16.dp)
//    ) {
//        IconButton(
//            onClick = onBackClick,
//            modifier = Modifier.align(Alignment.CenterStart)
//        ) {
//            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
//        }
//        Text(
//            text = title,
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            color = TextDark,
//            modifier = Modifier.align(Alignment.Center)
//        )
//    }
//}
//
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




