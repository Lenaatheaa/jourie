package com.example.jourie.presentation.journal.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jourie.presentation.journal.add.components.*
import com.example.jourie.ui.theme.White
import kotlinx.coroutines.android.awaitFrame

@Composable
fun AddNewJournalScreen(
        navController: NavController,
        onJournalSubmitted: (String, String) -> Unit,
        viewModel: AddNewJournalViewModel = viewModel()
) {
        val state by viewModel.state.collectAsState()
        val focusRequester = remember { FocusRequester() }
        val keyboardController = LocalSoftwareKeyboardController.current

        LaunchedEffect(keyboardController) {
                awaitFrame()
                focusRequester.requestFocus()
                keyboardController?.show()
        }

        Column(
                modifier =
                        Modifier.fillMaxSize()
                                .statusBarsPadding()
        ) {
                // Header
                ScreenHeader(
                        currentDate = state.currentDate,
                        onBackClick = { navController.popBackStack() }
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Mood Selection Grid
                MoodSelectionGrid(
                        selectedMood = state.mood,
                        onMoodSelected = viewModel::onMoodChange,
                        modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Content Input Field - Dynamic height with weight
                Box(
                        modifier =
                                Modifier.weight(1f)
                                        .padding(horizontal = 16.dp)
                                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp))
                ) {
                        Card(
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = White)
                        ) {
                                ContentInputField(
                                        content = state.content,
                                        onContentChange = viewModel::onContentChange,
                                        focusRequester = focusRequester,
                                        modifier = Modifier.fillMaxSize()
                                )
                        }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Action Button - Stick to bottom near navigation bar
                SaveEntryButton(
                        onClick = { viewModel.onSubmit(onJournalSubmitted) },
                        modifier = Modifier.padding(horizontal = 16.dp)
                )
                
                Spacer(modifier = Modifier.navigationBarsPadding().padding(bottom = 8.dp))
        }
}
