// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/dashboard/components/WellnessRecommendations.kt

package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.data.model.WellnessRecommendation
import com.example.jourie.ui.theme.JourieTheme
import com.example.jourie.ui.theme.PrimaryPurple // <-- DIPERBAIKI: Import yang hilang ditambahkan
import com.example.jourie.ui.theme.PrimaryPurplePastel
import com.example.jourie.ui.theme.TextDark
import com.example.jourie.ui.theme.White

@Composable
fun WellnessRecommendations(recommendations: List<WellnessRecommendation>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Personalized Recommendations",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
        recommendations.forEach { recommendation ->
            RecommendationCard(recommendation = recommendation)
        }
    }
}

@Composable
private fun RecommendationCard(recommendation: WellnessRecommendation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp, PrimaryPurplePastel)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = recommendation.category,
                color = PrimaryPurple, // Baris ini sekarang tidak akan error
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = recommendation.title,
                color = TextDark,
                fontSize = 16.sp
            )
        }
    }
}

// Preview ditambahkan untuk memudahkan pengecekan UI
@Preview(showBackground = true)
@Composable
private fun WellnessRecommendationsPreview() {
    val sampleRecommendations = listOf(
        WellnessRecommendation(1, "Wellness", "Meditation guide"),
        WellnessRecommendation(2, "Meditation for Beginners", "Calm your mind")
    )
    JourieTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            WellnessRecommendations(recommendations = sampleRecommendations)
        }
    }
}
