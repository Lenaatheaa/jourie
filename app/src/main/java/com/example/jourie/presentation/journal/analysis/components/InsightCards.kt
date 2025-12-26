package com.example.jourie.presentation.journal.analysis.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Blue400
import com.example.jourie.ui.theme.Blue50
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.Green50
import com.example.jourie.ui.theme.Green500

@Composable
fun InsightCards(sentimentScore: Int, rootCauseText: String, recommendationText: String) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Root Cause Card - Light Blue Background
                InsightCard(
                        icon = Icons.Default.Search,
                        iconBackgroundColor = Blue50, // Light blue circle
                        iconTint = Blue400,
                        title = "Root Cause",
                        content = rootCauseText,
                        cardBackgroundColor = Blue50 // Very light blue card background
                )

                // Recommendation Card - Light Green Background
                InsightCard(
                        icon = Icons.Default.Lightbulb,
                        iconBackgroundColor = Green50, // Light green circle
                        iconTint = Green500,
                        title = "Recommendation",
                        content = recommendationText,
                        cardBackgroundColor = Green50 // Very light green card background
                )
        }
}

@Composable
private fun InsightCard(
        icon: ImageVector,
        iconBackgroundColor: Color,
        iconTint: Color,
        title: String,
        content: String,
        cardBackgroundColor: Color
) {
        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
                elevation =
                        CardDefaults.cardElevation(
                                defaultElevation = 0.dp
                        ) // No shadow, flat design
        ) {
                Column(modifier = Modifier.padding(20.dp)) {
                        // Header with icon
                        Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                        ) {
                                Box(
                                        modifier =
                                                Modifier.size(40.dp)
                                                        .background(
                                                                iconBackgroundColor,
                                                                CircleShape
                                                        ),
                                        contentAlignment = Alignment.Center
                                ) {
                                        Icon(
                                                imageVector = icon,
                                                contentDescription = title,
                                                tint = iconTint,
                                                modifier = Modifier.size(20.dp)
                                        )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                        text = title,
                                        fontWeight = FontWeight.Bold,
                                        color = Gray900,
                                        fontSize = 18.sp
                                )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Content text
                        Text(
                                text = content,
                                fontSize = 15.sp,
                                color = Gray900.copy(alpha = 0.8f),
                                lineHeight = 22.sp
                        )
                }
        }
}
