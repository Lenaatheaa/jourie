package com.example.jourie.presentation.journal.add.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Gray500
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

data class Mood(val emoji: String, val label: String)

val moods =
        listOf(
                Mood("😊", "Happy"),
                Mood("😐", "Neutral"),
                Mood("😰", "Anxious"),
                Mood("😢", "Sad"),
                Mood("🤩", "Excited"),
                Mood("😌", "Calm"),
                Mood("😠", "Angry"),
                Mood("😔", "Disappointed"),
                Mood("🙏", "Grateful"),
                Mood("😴", "Tired"),
                Mood("😕", "Confused"),
                Mood("🤗", "Loved"),
                Mood("😱", "Scared"),
                Mood("🥳", "Joyful"),
                Mood("😞", "Hopeless"),
                Mood("😤", "Frustrated"),
                Mood("🤔", "Thoughtful"),
                Mood("😩", "Overwhelmed")
        )

@Composable
fun MoodSelectionGrid(
        selectedMood: String,
        onMoodSelected: (String) -> Unit,
        modifier: Modifier = Modifier
) {
        Column(modifier = modifier) {
                Text(
                        text = "How are you feeling?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Gray900,
                        modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
                        modifier = Modifier.height(200.dp) // Tampilkan 2 baris, sisanya scroll
                ) {
                        items(moods) { mood ->
                                MoodCard(
                                        emoji = mood.emoji,
                                        label = mood.label,
                                        isSelected = selectedMood == mood.label,
                                        onClick = { onMoodSelected(mood.label) }
                                )
                        }
                }
        }
}

@Composable
private fun MoodCard(emoji: String, label: String, isSelected: Boolean, onClick: () -> Unit) {
        Card(
                modifier =
                        Modifier.fillMaxWidth()
                                .aspectRatio(1f)
                                .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                ) { onClick() }
                                .then(
                                        if (isSelected) {
                                                Modifier.border(
                                                        2.dp,
                                                        Purple500,
                                                        RoundedCornerShape(16.dp)
                                                )
                                        } else {
                                                Modifier
                                        }
                                ),
                shape = RoundedCornerShape(16.dp),
                colors =
                        CardDefaults.cardColors(
                                containerColor =
                                        if (isSelected) Purple500.copy(alpha = 0.1f) else White
                        ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
                Column(
                        modifier =
                                Modifier.fillMaxSize().background(Color.Transparent).padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                ) {
                        Text(text = emoji, fontSize = 32.sp, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                                text = label,
                                fontSize = 12.sp,
                                fontWeight =
                                        if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) Purple500 else Gray500,
                                textAlign = TextAlign.Center
                        )
                }
        }
}
