package com.example.jourie.presentation.journal.analysis.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jourie.ui.theme.Purple500

@Composable
fun AnalysisActionButtons(onShare: () -> Unit, onDownload: () -> Unit) {
        Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
                // Download and Share Buttons
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                        // Download Button
                        OutlinedButton(
                                onClick = onDownload,
                                modifier = Modifier.weight(1f).height(52.dp),
                                colors =
                                        ButtonDefaults.outlinedButtonColors(
                                                contentColor = Purple500
                                        ),
                                border = BorderStroke(1.5.dp, Purple500),
                                shape = RoundedCornerShape(16.dp)
                        ) {
                                Icon(
                                        imageVector = Icons.Default.Download,
                                        contentDescription = "Download",
                                        modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Download")
                        }

                        // Share Button
                        OutlinedButton(
                                onClick = onShare,
                                modifier = Modifier.weight(1f).height(52.dp),
                                colors =
                                        ButtonDefaults.outlinedButtonColors(
                                                contentColor = Purple500
                                        ),
                                border = BorderStroke(1.5.dp, Purple500),
                                shape = RoundedCornerShape(16.dp)
                        ) {
                                Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = "Share",
                                        modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Share")
                        }
                }

                // Auto-save confirmation text
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        Text(
                                text = "✓ Analysis saved automatically",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                        )
                }
        }
}
