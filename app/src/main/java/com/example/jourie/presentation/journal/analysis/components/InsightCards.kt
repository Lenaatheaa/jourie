package com.example.jourie.presentation.journal.analysis.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.PrimaryBlue
import com.example.jourie.ui.theme.TextDark

@Composable
fun InsightCards(sentimentScore: Int, rootCauseText: String, recommendationText: String) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Root Cause
        InsightCard(
                icon = Icons.Default.Search,
                iconColor = Color(0xFF4A90E2),
                title = "Root Cause",
                content = rootCauseText
        )
        // Recommendation
        InsightCard(
                icon = Icons.Default.Lightbulb,
                iconColor = PrimaryBlue,
                title = "Recommendation",
                content = recommendationText
        )
        // Sentiment Score
    }
}

@Composable
private fun InsightCard(icon: ImageVector, iconColor: Color, title: String, content: String) {
    InsightCard(icon = icon, iconColor = iconColor, title = title) {
        Text(text = content, fontSize = 14.sp, color = TextDark.copy(alpha = 0.8f))
    }
}

@Composable
private fun InsightCard(
        icon: ImageVector,
        iconColor: Color,
        title: String,
        content: @Composable () -> Unit
) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = iconColor.copy(alpha = 0.1f)),
            border = BorderStroke(1.dp, iconColor.copy(alpha = 0.3f))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp).padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(modifier = Modifier.height(4.dp))
                content()
            }
        }
    }
}
