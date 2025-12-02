

package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items // DIPERBAIKI: Menggunakan 'items'
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
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DailyCalendarView
            () {
    // DIPERBAIKI: Menggunakan Calendar sebagai ganti LocalDate
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val dates = remember {
        (-2..2).map {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, it)
            calendar        }
    }

    Column {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Calendar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,color = TextDark
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { /* TODO: Logika dropdown bulan */ }
            ) {
                // Tampilkan nama bulan saat ini
                val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
                Text(text = monthFormat.format(selectedDate.time), color = PrimaryPurple, fontWeight = FontWeight.SemiBold)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Month", tint = PrimaryPurple)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(dates) { date ->
                DateItem(
                    date = date,
                    isSelected = date.get(Calendar.DAY_OF_YEAR) == selectedDate.get(Calendar.DAY_OF_YEAR),
                    onDateClick = { selectedDate = it }
                )
            }
        }
    }
}

@Composable
private fun DateItem(date: Calendar, isSelected: Boolean, onDateClick: (Calendar) -> Unit) {
    val backgroundColor = if (isSelected) PrimaryPurple else White
    val textColor = if (isSelected) White else TextDark

    // Format untuk hari (e.g., Tue) dan tanggal (e.g., 29)
    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val dateFormat = SimpleDateFormat("d", Locale.getDefault())

    Column(
        modifier = Modifier
            .size(width = 60.dp, height = 70.dp)
            // DIUBAH: Sudut diseragamkan menjadi 10.dp
        .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable { onDateClick(date) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = dayFormat.format(date.time),
            color = textColor.copy(alpha = 0.8f),
            fontSize = 12.sp
        )
        Text(
            text = dateFormat.format(date.time),
            color = textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}











