// File: .../presentation/journal/add/AddNewJournalScreen.kt

package com.example.jourie.presentation.journal.add

import androidx.compose.foundation.layout.*import androidx.compose.foundation.shape.RoundedCornerShape
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
    // DIPERBAIKI: Tambahkan parameter untuk aksi submit
    onJournalSubmitted: (String) -> Unit,
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

    // DIHAPUS: Logika 'isSaved' tidak diperlukan lagi di sini
    // LaunchedEffect(state.isSaved) { ... }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            currentDate = state.currentDate,
            onBackClick = { navController.popBackStack() }
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .offset(y = (-20).dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                .weight(1f)
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
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MediaActionButtons()
            // DIPERBAIKI: onClick sekarang meneruskan aksi navigasi ke ViewModel
            SaveEntryButton(onClick = { viewModel.onSubmit(onJournalSubmitted) })
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
