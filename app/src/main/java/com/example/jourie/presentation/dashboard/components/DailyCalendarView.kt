

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
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.Dp
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.PrimaryPurplePastel
import com.example.jourie.ui.theme.TextDark
import com.example.jourie.ui.theme.White
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.border
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.shape.CircleShape

@Composable
fun DailyCalendarView
            () {
    // DIPERBAIKI: Menggunakan Calendar sebagai ganti LocalDate
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }

    // Build a grid (weeks) of nullable Ints representing day numbers for the current month
    val monthGrid = remember(selectedDate.timeInMillis) {
        val cal = selectedDate.clone() as Calendar
        cal.set(Calendar.DAY_OF_MONTH, 1)
        val firstDow = cal.get(Calendar.DAY_OF_WEEK) // Sunday=1
        // convert to Monday-start index (0..6)
        val offset = (firstDow + 6) % 7
        val maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        val list = mutableListOf<Int?>()
        repeat(offset) { list.add(null) }
        for (d in 1..maxDay) list.add(d)
        while (list.size % 7 != 0) list.add(null)
        // chunk into weeks
        val weeks = list.chunked(7)
        weeks
    }

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFE0C0FF), RoundedCornerShape(16.dp))
                .padding(12.dp)) {

                // Header inside card: small label + month title + nav
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                        Text(text = monthFormat.format(selectedDate.time), color = TextDark, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.ArrowBackIos,
                            contentDescription = "Previous Month",
                            tint = PrimaryPurple,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    val newCal = selectedDate.clone() as Calendar
                                    newCal.add(Calendar.MONTH, -1)
                                    selectedDate = newCal
                                }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.Default.ArrowForwardIos,
                            contentDescription = "Next Month",
                            tint = PrimaryPurple,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    val newCal = selectedDate.clone() as Calendar
                                    newCal.add(Calendar.MONTH, 1)
                                    selectedDate = newCal
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // weekday headers (Mon..Sun) - single-letter and centered per column
                Row(modifier = Modifier.fillMaxWidth()) {
                    val days = listOf("M","T","W","T","F","S","S")
                    days.forEach { d ->
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            Text(text = d, color = PrimaryPurplePastel, maxLines = 1)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // weeks
                val cellOuterWidth = 64.dp
                val circleSize = 48.dp
                monthGrid.forEach { week ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        week.forEach { day ->
                            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                                DateItem(day = day, selectedDate = selectedDate, onDayClick = { dayInt ->
                                    val newCal = selectedDate.clone() as Calendar
                                    newCal.set(Calendar.DAY_OF_MONTH, dayInt)
                                    selectedDate = newCal
                                }, circleSize = circleSize)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun DateItem(day: Int?, selectedDate: Calendar, onDayClick: (Int) -> Unit, circleSize: Dp) {
    if (day == null) {
        // empty cell: show circular outline
        Box(modifier = Modifier
            .size(circleSize)
            .clip(CircleShape)
            .border(1.dp, PrimaryPurplePastel, CircleShape)
            .background(Color.Transparent))
        return
    }

    val isSelected = selectedDate.get(Calendar.DAY_OF_MONTH) == day

    val backgroundColor = if (isSelected) PrimaryPurple else White
    val textColor = if (isSelected) White else TextDark

    Box(
        modifier = Modifier
            .size(circleSize)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(1.dp, PrimaryPurplePastel, CircleShape)
            .clickable { onDayClick(day) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = day.toString(), color = textColor, fontWeight = FontWeight.Bold)
    }
}











