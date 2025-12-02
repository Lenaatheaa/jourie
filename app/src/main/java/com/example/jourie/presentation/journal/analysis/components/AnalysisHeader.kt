package com.example.jourie.presentation.journal.analysis.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.White

@Composable
fun AnalysisHeader(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp
            ))
            .background(PrimaryPurple)
            .padding(horizontal = 16.dp, vertical = 20.dp),        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = White,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable(onClick = onBackClick)
                .padding(8.dp)
        )
        Text(
            text = "Today",
            color = White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("😊", fontSize = 24.sp)
            Text("Calm", color = White.copy(alpha = 0.8f), fontSize = 12.sp)
        }
    }
}




