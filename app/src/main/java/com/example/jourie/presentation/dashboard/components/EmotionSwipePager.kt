package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.data.model.EmotionSnapshot
import com.example.jourie.ui.theme.GreenCheck
import com.example.jourie.ui.theme.PrimaryPurple

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmotionSwipePager(emotions: List<EmotionSnapshot>) {
    if (emotions.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { emotions.size })

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Menggunakan PageSize.Fixed agar kartu terlihat seperti daftar horizontal
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSize = PageSize.Fixed(160.dp), 
            pageSpacing = 12.dp,
            verticalAlignment = Alignment.Top
        ) { page ->
            val emotion = emotions[page]
            EmotionSummaryCard(emotion = emotion)
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Indikator pager
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) PrimaryPurple else PrimaryPurple.copy(alpha = 0.3f)
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@Composable
private fun EmotionSummaryCard(emotion: EmotionSnapshot) {
    Card(
        modifier = Modifier
<<<<<<< HEAD
            .width(200.dp) // Ukuran card pager
            .clip(RoundedCornerShape(10.dp))
            .background(emotion.color.copy(alpha = 0.1f))
            .padding(16.dp)
=======
            .width(150.dp)
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE)) // Border abu-abu tipis
>>>>>>> 928cb41d19870e16c1c47c6263196f6997421823
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            // Nama Emosi (Abu-abu)
            Text(
                text = emotion.name,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Gray
            )
            
            // Persentase (Hitam Bold - Sesuai Permintaan)
            Text(
                text = "${emotion.percentage}%",
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                color = Color.Black
            )
            
            // Perubahan: hijau jika positif (tidak diawali '-') dan merah jika negatif (diawali '-')
            val isNegative = emotion.change.trimStart().startsWith("-")
            val changeColor = if (isNegative) Color.Red else GreenCheck
            Text(
                text = emotion.change,
                fontSize = 14.sp,
                color = changeColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
