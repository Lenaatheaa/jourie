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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Purple400
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AnalysisHeader(
    onBackClick: () -> Unit, 
    dominantEmotion: String = "Happy",
    journalTimestamp: Long? = null
) {
        Box(
                modifier =
                        Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                                .background(
                                        Brush.horizontalGradient(
                                                colors = listOf(Purple400, Purple500)
                                        )
                                )
                                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
                // Back Button
                Box(
                        modifier =
                                Modifier.align(Alignment.CenterStart)
                                        .size(40.dp)
                                        .background(White.copy(alpha = 0.3f), CircleShape)
                                        .clickable(onClick = onBackClick),
                        contentAlignment = Alignment.Center
                ) {
                        Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = White,
                                modifier = Modifier.size(20.dp)
                        )
                }

                // Center Content - Title and Date
                Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Text(
                                text = "Analysis Results",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        // Display tanggal journal jika ada, fallback ke tanggal hari ini
                        val dateFormat = SimpleDateFormat("EEEE, MMMM dd", Locale.getDefault())
                        val displayDate = if (journalTimestamp != null) {
                            dateFormat.format(Date(journalTimestamp))
                        } else {
                            dateFormat.format(Date())
                        }
                        Text(text = displayDate, fontSize = 14.sp, color = White.copy(alpha = 0.9f))
                }

                // Mood Emoji on the right
                Column(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Text(text = getEmojiForMood(dominantEmotion), fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                                text = dominantEmotion,
                                fontSize = 12.sp,
                                color = White,
                                fontWeight = FontWeight.Medium
                        )
                }
        }
}

private fun getEmojiForMood(mood: String): String {
        return when (mood.lowercase()) {
                "senang", "happy", "happiness" -> "😊"
                "sedih", "sad", "sadness" -> "😢"
                "cemas", "anxious", "anxiety", "fear" -> "😰"
                "marah", "angry", "anger" -> "😠"
                "tenang", "calm", "peace" -> "😌"
                "neutral" -> "😐"
                "excited", "excitement" -> "🤩"
                "disappointed", "disappointment" -> "😔"
                "grateful", "thankful", "gratitude" -> "🙏"
                "tired", "exhausted", "sleepy" -> "😴"
                "confused", "confusion" -> "😕"
                "loved", "love", "loving" -> "🤗"
                "scared", "afraid", "terrified" -> "😱"
                "joyful", "joy", "cheerful" -> "🥳"
                "hopeless", "despair", "desperate" -> "😞"
                "frustrated", "frustration" -> "😤"
                "thoughtful", "thinking", "pensive" -> "🤔"
                "overwhelmed", "stressed", "pressure" -> "😩"
                else -> "😊"
        }
}
