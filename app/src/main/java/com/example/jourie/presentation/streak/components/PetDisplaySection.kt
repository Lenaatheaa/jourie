// File: .../presentation/streak/components/PetDisplaySection.kt
package com.example.jourie.presentation.streak.components

// --- IMPORT YANG DIPERLUKAN UNTUK GIF ---
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage // <-- DITAMBAHKAN
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.jourie.R // <-- DITAMBAHKAN
import com.example.jourie.ui.theme.Gray200
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.Purple50

@Composable
fun PetDisplaySection(level: Int) {
    // Di sini kita membuat ImageLoader khusus untuk GIF
    val context = LocalContext.current
    val imageLoader =
            ImageLoader.Builder(context)
                    .components {
                        if (SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()

    Box(
            modifier =
                    Modifier.fillMaxWidth()
                            .height(240.dp)
                            .background(Purple50, RoundedCornerShape(16.dp))
                            .border(1.dp, Gray200, RoundedCornerShape(16.dp))
                            .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
    ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            // --- DIPERBAIKI: Mengganti Text emoji dengan AsyncImage untuk GIF ---
            AsyncImage(
                    // GANTI NAMA FILE GIF ANDA DI SINI JIKA BERBEDA
                    model = R.drawable.capybara_walk,
                    contentDescription = "Evolving Pet GIF",
                    imageLoader = imageLoader,
                    modifier = Modifier.size(150.dp) // Sesuaikan ukuran GIF agar pas
            )
            // -------------------------------------------------------------

            Spacer(modifier = Modifier.height(16.dp))

            // Teks Level (TETAP SAMA)
            Text(
                    text = "✨ Level $level",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray900
            )
        }
    }
}

@Preview
@Composable
private fun PetDisplaySectionPreview() {
    JourieTheme { PetDisplaySection(level = 1) }
}
