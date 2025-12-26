package com.example.jourie.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DailyCalendarView(
        journalEntries: List<com.example.jourie.data.model.JournalEntry>,
        onDateWithJournalClick: (String) -> Unit
) {
    // DIPERBAIKI: Menggunakan Calendar sebagai ganti LocalDate
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }

    // Build a grid (weeks) of nullable Ints representing day numbers for the current month
    val monthGrid =
            remember(selectedDate.timeInMillis) {
                val cal = selectedDate.clone() as Calendar
                cal.set(Calendar.DAY_OF_MONTH, 1)
                val firstDow = cal.get(Calendar.DAY_OF_WEEK) // Sunday=1
                // convert to Monday-start index (0..6): Monday=0, ..., Sunday=6
                val offset = (firstDow + 5) % 7
                val maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

                val list = mutableListOf<Int?>()
                repeat(offset) { list.add(null) }
                for (d in 1..maxDay) list.add(d)
                while (list.size % 7 != 0) list.add(null)
                // chunk into weeks
                val weeks = list.chunked(7)
                weeks
            }

    // Hitung hari-hari di bulan ini yang memiliki jurnal
    val monthAbbrev =
            remember(selectedDate.timeInMillis) {
                java.text.SimpleDateFormat("MMM", java.util.Locale.getDefault())
                        .format(selectedDate.time)
            }
    val journalDaysInMonth =
            remember(selectedDate.timeInMillis, journalEntries) {
                journalEntries
                        .filter { it.monthAbbreviation == monthAbbrev }
                        .map { it.dayOfMonth }
                        .toSet()
            }

    Column {
        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {

                // Header inside card: small label + month title + nav
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = Purple500,
                                    modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                            Text(
                                    text = monthFormat.format(selectedDate.time),
                                    color = Gray900,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                                Icons.Default.ArrowBackIos,
                                contentDescription = "Previous Month",
                                tint = Purple500,
                                modifier =
                                        Modifier.size(20.dp).clickable {
                                            val newCal = selectedDate.clone() as Calendar
                                            newCal.add(Calendar.MONTH, -1)
                                            selectedDate = newCal
                                        }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                                Icons.Default.ArrowForwardIos,
                                contentDescription = "Next Month",
                                tint = Purple500,
                                modifier =
                                        Modifier.size(20.dp).clickable {
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
                    val days = listOf("M", "T", "W", "T", "F", "S", "S")
                    days.forEach { d ->
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            Text(text = d, color = Purple500, maxLines = 1)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // weeks
                val circleSize = 48.dp
                monthGrid.forEach { week ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        week.forEach { day ->
                            Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                            ) {
                                val hasJournal = day != null && journalDaysInMonth.contains(day)
                                DateItem(
                                        day = day,
                                        selectedDate = selectedDate,
                                        onDayClick = { dayInt ->
                                            val newCal = selectedDate.clone() as Calendar
                                            newCal.set(Calendar.DAY_OF_MONTH, dayInt)
                                            selectedDate = newCal

                                            if (hasJournal) {
                                                val dateQuery = "$dayInt $monthAbbrev"
                                                onDateWithJournalClick(dateQuery)
                                            }
                                        },
                                        circleSize = circleSize
                                )
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
        // empty cell: just spacer
        Box(modifier = Modifier.size(circleSize))
        return
    }

    val isSelected = selectedDate.get(Calendar.DAY_OF_MONTH) == day

    val backgroundColor = if (isSelected) Purple500 else Color.Transparent
    val textColor = if (isSelected) White else Gray900

    Box(
            modifier =
                    Modifier.size(circleSize)
                            .clip(RoundedCornerShape(12.dp))
                            .background(backgroundColor)
                            .clickable { onDayClick(day) },
            contentAlignment = Alignment.Center
    ) { Text(text = day.toString(), color = textColor, fontWeight = FontWeight.Normal) }
}
