package com.example.jourie.presentation.history.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.jourie.ui.theme.Gray200
import com.example.jourie.ui.theme.Gray400
import com.example.jourie.ui.theme.Purple500
import com.example.jourie.ui.theme.White

@Composable
fun JournalSearchBar(query: String, onQueryChange: (String) -> Unit) {
        OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier =
                        Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .shadow(2.dp, RoundedCornerShape(12.dp))
                                .height(56.dp),
                placeholder = {
                        Text("Search your journals...", color = Gray400.copy(alpha = 0.6f))
                },
                leadingIcon = {
                        Icon(
                                Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = Gray400
                        )
                },
                shape = RoundedCornerShape(12.dp),
                colors =
                        OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = White,
                                unfocusedContainerColor = White,
                                disabledContainerColor = White,
                                focusedBorderColor = Purple500,
                                unfocusedBorderColor = Gray200,
                                cursorColor = Purple500,
                                focusedLeadingIconColor = Purple500,
                                unfocusedLeadingIconColor = Gray400
                        ),
                singleLine = true
        )
}
