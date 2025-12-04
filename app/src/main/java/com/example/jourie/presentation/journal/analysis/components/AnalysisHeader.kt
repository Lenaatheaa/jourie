package com.example.jourie.presentation.journal.analysis.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.White

@Composable
fun AnalysisHeader(onBackClick: () -> Unit) {
    // Outer container provides the same horizontal inset as EntrySummaryCard
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Inner pill matches card width (fills available width inside outer padding)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(6.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(PrimaryPurple)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            // Center title
            Text(
                text = "Today",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Back icon on the left
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(50.dp))
                    .background(White.copy(alpha = 0.12f))
                    .border(1.dp, White.copy(alpha = 0.18f), RoundedCornerShape(50.dp))
                    .clickable(onClick = onBackClick)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.size(16.dp)
                )
            }

            // Emoji + label on the right
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(start = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("😊", fontSize = 22.sp)
                Text("Calm", color = White.copy(alpha = 0.9f), fontSize = 12.sp)
            }
        }
    }
}



