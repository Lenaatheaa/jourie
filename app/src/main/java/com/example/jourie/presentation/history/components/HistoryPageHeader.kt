// File: A:/androiddstudioo/Jourie/app/src/main/java/com/example/jourie/presentation/history/components/HistoryPageHeader.kt

package com.example.jourie.presentation.history.components

// DIPERBAIKI: Blok import sudah bersih dari karakter tambahan
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.presentation.history.components.JournalSearchBar

// Header dengan nama unik
@Composable
fun HistoryPageHeader() {
<<<<<<< HEAD
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
            .background(PrimaryPurple),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Journal History",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
=======
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(PrimaryPurple),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Journal History",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
>>>>>>> 928cb41d19870e16c1c47c6263196f6997421823
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryPageHeaderPreview() {
    HistoryPageHeader()
}

