package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.data.model.EmotionSnapshot
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.TextDark

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmotionSwipePager(emotions: List<EmotionSnapshot>) {
    if (emotions.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { emotions.size })

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 48.dp), // Beri ruang di sisi
            pageSpacing = 16.dp
        ) { page ->
            val emotion = emotions[page]
            EmotionSummaryCard(emotion = emotion)
        }
        Spacer(modifier = Modifier.height(12.dp))
        // Swipe Indicator Dots
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
    Column(
        modifier = Modifier
            .width(200.dp) // Ukuran card pager
            .clip(RoundedCornerShape(10.dp))
            .background(emotion.color.copy(alpha = 0.1f))
            .padding(16.dp)
    ) {
        Text(text = emotion.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
        Text(text = "${emotion.percentage}%", fontWeight = FontWeight.Bold, fontSize = 28.sp, color = emotion.color)
        Text(text = emotion.change, fontSize = 12.sp, color = TextDark)
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { emotion.percentage / 100f },
            color = emotion.color,
            trackColor = emotion.color.copy(alpha = 0.3f),
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape)
        )
    }
}
