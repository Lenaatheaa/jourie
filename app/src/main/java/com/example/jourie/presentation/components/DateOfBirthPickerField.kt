package com.example.jourie.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Gray400
import com.example.jourie.ui.theme.Gray50
import com.example.jourie.ui.theme.Gray200
import com.example.jourie.ui.theme.Gray500
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.Purple500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateOfBirthPickerField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier
) {
        var showDatePicker by remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState()
        val interactionSource = remember { MutableInteractionSource() }

        // Detect when field is pressed
        LaunchedEffect(interactionSource) {
                interactionSource.interactions.collect { interaction ->
                        if (interaction is PressInteraction.Press) {
                                showDatePicker = true
                        }
                }
        }

        OutlinedTextField(
                value = value,
                onValueChange = { },
                modifier = modifier.fillMaxWidth(),
                label = { Text("Date of Birth (Optional)") },
                trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                        imageVector = Icons.Default.CalendarToday,
                                        contentDescription = "Select Date",
                                        tint = if (value.isNotEmpty()) Purple500 else Gray400
                                )
                        }
                },
                readOnly = true,
                interactionSource = interactionSource,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Purple500,
                        unfocusedBorderColor = Gray200,
                        focusedContainerColor = Gray50,
                        unfocusedContainerColor = Gray50,
                        focusedLabelColor = Purple500,
                        unfocusedLabelColor = Gray400,
                        disabledTextColor = Gray900
                ),
                textStyle = TextStyle(fontSize = 14.sp, color = Gray900)
        )

        if (showDatePicker) {
                DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                                TextButton(onClick = {
                                        val selectedMillis = datePickerState.selectedDateMillis
                                        if (selectedMillis != null) {
                                                val formatter = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.ENGLISH)
                                                val date = java.util.Date(selectedMillis)
                                                onValueChange(formatter.format(date))
                                        }
                                        showDatePicker = false
                                }) {
                                        Text("OK", color = Purple500, fontWeight = FontWeight.Bold)
                                }
                        },
                        dismissButton = {
                                TextButton(onClick = { showDatePicker = false }) {
                                        Text("Cancel", color = Gray500)
                                }
                        }
                ) {
                        DatePicker(
                                state = datePickerState,
                                colors = DatePickerDefaults.colors(
                                        selectedDayContainerColor = Purple500,
                                        todayContentColor = Purple500,
                                        todayDateBorderColor = Purple500
                                )
                        )
                }
        }
}
