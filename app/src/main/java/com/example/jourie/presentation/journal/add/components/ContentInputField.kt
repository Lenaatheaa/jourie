package com.example.jourie.presentation.journal.add.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jourie.ui.theme.Gray400
import com.example.jourie.ui.theme.Gray900
import com.example.jourie.ui.theme.Purple500

@Composable
fun ContentInputField(
        content: String,
        onContentChange: (String) -> Unit,
        focusRequester: FocusRequester,
        modifier: Modifier = Modifier
) {
        val interactionSource = remember { MutableInteractionSource() }

        Column(modifier = modifier) {
                TextField(
                        value = content,
                        onValueChange = onContentChange,
                        modifier =
                                Modifier.fillMaxWidth().weight(1f).focusRequester(focusRequester),
                        placeholder = {
                                Text(
                                        text = "How are you feeling today? ✨",
                                        color = Gray400,
                                        fontSize = 15.sp
                                )
                        },
                        colors =
                                TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        disabledContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent
                                ),
                        textStyle =
                                TextStyle(color = Gray900, fontSize = 15.sp, lineHeight = 22.sp),
                        interactionSource = interactionSource
                )

                // Word & Character Counter
                Row(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.Start
                ) {
                        val wordCount =
                                if (content.isBlank()) 0
                                else
                                        content.trim()
                                                .split("\\s+".toRegex())
                                                .filter { it.isNotBlank() }
                                                .size
                        val charCount = content.length

                        Text(text = "$wordCount words", fontSize = 12.sp, color = Purple500)
                        Text(text = " • ", fontSize = 12.sp, color = Gray400)
                        Text(text = "$charCount characters", fontSize = 12.sp, color = Gray400)
                }
        }
}
