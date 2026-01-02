package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.data.model.WellnessRecommendation
import com.example.jourie.ui.theme.Gray500
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.Pink100
import com.example.jourie.ui.theme.Purple100
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.Purple600
import com.example.jourie.ui.theme.White

@Composable
fun WellnessRecommendations(recommendations: List<WellnessRecommendation>) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                        Box(
                                modifier =
                                        Modifier.size(40.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(Purple500),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = White,
                                        modifier = Modifier.size(22.dp)
                                )
                        }
                        Text(
                                text = "AI Recommendations",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Gray900
                        )
                }

                recommendations.forEachIndexed { index, recommendation ->
                        RecommendationCard(
                                recommendation = recommendation,
                                backgroundColor = if (index == 0) Pink100 else Purple100,
                                iconColor = if (index == 0) Color(0xFFEC4899) else Purple600
                        )
                }
        }
}

@Composable
private fun RecommendationCard(
        recommendation: WellnessRecommendation,
        backgroundColor: Color,
        iconColor: Color
) {
        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = backgroundColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
                Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                                Box(
                                        modifier =
                                                Modifier.size(44.dp)
                                                        .clip(CircleShape)
                                                        .background(iconColor.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                ) {
                                        Icon(
                                                imageVector =
                                                        if (recommendation.category.contains(
                                                                        "Recommendation",
                                                                        ignoreCase = true
                                                                )
                                                        )
                                                                Icons.Default.Favorite
                                                        else Icons.Default.SelfImprovement,
                                                contentDescription = null,
                                                tint = iconColor,
                                                modifier = Modifier.size(24.dp)
                                        )
                                }

                                Column {
                                        Text(
                                                text = recommendation.category,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Gray900
                                        )
                                        Text(
                                                text = "Personalized for you",
                                                fontSize = 12.sp,
                                                color = Gray500
                                        )
                                }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                                text = recommendation.title,
                                fontSize = 14.sp,
                                color = Gray900,
                                lineHeight = 20.sp
                        )
                }
        }
}

@Preview(showBackground = true)
@Composable
private fun WellnessRecommendationsPreview() {
        val sampleRecommendations =
                listOf(
                        WellnessRecommendation(
                                1,
                                "Recommendation",
                                "Sangat penting untuk diingat bahwa Anda tidak bertanggung jawab atas pilihan atau tindakan orang lain."
                        ),
                        WellnessRecommendation(
                                2,
                                "Quote",
                                "Anda tidak bisa mengendalikan tindakan orang lain, tapi Anda selalu bisa mengendalikan reaksi Anda."
                        )
                )
        JourieTheme {
                Column(modifier = Modifier.padding(16.dp)) {
                        WellnessRecommendations(recommendations = sampleRecommendations)
                }
        }
}
