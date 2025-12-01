package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.TextDark
import com.example.jourie.ui.theme.White
import java.time.LocalDate

@Composable
fun DailyCalendarView() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val dates = (-2..2).map { LocalDate.now().plusDays(it.toLong()) }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Calendar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { /* Logika dropdown bulan */ }
            ) {
                Text(text = "November", color = PrimaryPurple, fontWeight = FontWeight.SemiBold)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Month", tint = PrimaryPurple)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            itemsIndexed(dates) { _, date ->
                DateItem(
                    date = date,
                    isSelected = date == selectedDate,
                    onDateClick = { selectedDate = it }
                )
            }
        }
    }
}

@Composable
private fun DateItem(date: LocalDate, isSelected: Boolean, onDateClick: (LocalDate) -> Unit) {
    val backgroundColor = if (isSelected) PrimaryPurple else White
    val textColor = if (isSelected) White else TextDark

    Column(
        modifier = Modifier
            .size(width = 60.dp, height = 70.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable { onDateClick(date) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = date.dayOfWeek.name.take(3), // e.g., Tue
            color = textColor.copy(alpha = 0.8f),
            fontSize = 12.sp
        )
        Text(
            text = date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
