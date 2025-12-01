package com.example.jourie.presentation.journal.analysis.components



import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jourie.ui.theme.PrimaryPurple
import com.example.jourie.ui.theme.White


@Composable
fun AnalysisActionButtons(
    onShare: () -> Unit,
    onDownload: () -> Unit,onSave: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = onShare,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, PrimaryPurple)
        ) {
            Text("Share with Friends", color = PrimaryPurple, fontWeight = FontWeight.SemiBold)
        }
        OutlinedButton(
            onClick = onDownload,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, PrimaryPurple)
        ) {
            Text("Download Report", color = PrimaryPurple, fontWeight = FontWeight.SemiBold)
        }

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            // --- BAGIAN YANG HILANG DIMULAI DARI SINI ---
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryPurple,
                contentColor = White // atau Color.White
            )
        ) {
            Text("Save All Insights", fontWeight = FontWeight.Bold)
        }

    }
}







