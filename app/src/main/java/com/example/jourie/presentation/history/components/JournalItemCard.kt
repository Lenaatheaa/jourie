package com.example.jourie.presentation.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.data.model.JournalEntry
import com.example.jourie.ui.theme.Gray400
import com.example.jourie.ui.theme.Gray50
import com.example.jourie.ui.theme.Gray500
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.Red500
import com.example.jourie.ui.theme.White

@Composable
fun JournalItemCard(entry: JournalEntry, onClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
            modifier =
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp).clickable {
                        onClick()
                    },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().height(130.dp), verticalAlignment = Alignment.CenterVertically) {
            // Left colored border
            Box(
                    modifier =
                            Modifier.width(4.dp)
                                    .height(130.dp)
                                    .background(getColorForMood(entry.mood))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Date badge
            DateBadge(day = entry.dayOfMonth.toString(), month = entry.monthAbbreviation)

            Spacer(modifier = Modifier.width(12.dp))

            // Content area
            Column(modifier = Modifier.weight(1f).padding(vertical = 12.dp)) {
                // Date label
                Text(
                        text = entry.dateLabel,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Gray900
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Mood with emoji
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = getMoodEmoji(entry.mood), fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = entry.mood, fontSize = 14.sp, color = Gray500)
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Journal preview text
                Text(
                        text = entry.description,
                        fontSize = 13.sp,
                        color = Gray500,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                )
            }

            // Right side: Delete icon and chevron
            Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(end = 8.dp)
            ) {
                IconButton(onClick = onDeleteClick, modifier = Modifier.size(32.dp)) {
                    Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Red500,
                            modifier = Modifier.size(20.dp)
                    )
                }

                Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "View Details",
                        tint = Gray400,
                        modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun DateBadge(day: String, month: String) {
    Column(
            modifier =
                    Modifier.width(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Gray50)
                            .padding(vertical = 8.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
                text = month.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Purple500,
                letterSpacing = 0.5.sp
        )
        Text(text = day, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Gray900)
    }
}

// Helper function untuk mendapatkan warna border berdasarkan mood
private fun getColorForMood(mood: String): Color {
    return when (mood.lowercase()) {
        "happy", "senang", "excited" -> Color(0xFF4CAF50) // Green
        "neutral", "calm", "tenang" -> Color(0xFF2196F3) // Blue
        "sad", "sedih" -> Color(0xFF9C27B0) // Purple
        "anxious", "cemas", "stressed" -> Color(0xFFFF9800) // Orange
        "angry", "marah" -> Color(0xFFF44336) // Red
        else -> Color(0xFF2196F3) // Default blue
    }
}

// Helper function untuk mendapatkan emoji berdasarkan mood
private fun getMoodEmoji(mood: String): String {
    return when (mood.lowercase()) {
        "happy" -> "😊"
        "neutral" -> "😐"
        "anxious" -> "😰"
        "sad" -> "😢"
        "excited" -> "🤩"
        "calm" -> "😌"
        "angry" -> "😠"
        "disappointed" -> "😔"
        "grateful" -> "🙏"
        "tired" -> "😴"
        "confused" -> "😕"
        "loved" -> "🤗"
        "scared" -> "😱"
        "joyful" -> "🥳"
        "hopeless" -> "😞"
        "frustrated" -> "😤"
        "thoughtful" -> "🤔"
        "overwhelmed" -> "😩"
        else -> "😐" // Default fallback
    }
}
