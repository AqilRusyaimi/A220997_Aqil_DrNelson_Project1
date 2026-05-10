package com.example.a220997_aqil_drnelson_project1

import androidx.compose.foundation.layout.R
import kotlin.text.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummaryScreen(
    uiState: ReportUiState,
    onSendClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            Text(
//                text = "Report Summary",
//                style = MaterialTheme.typography.headlineMedium,
//                color = MaterialTheme.colorScheme.primary
//            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    SummaryItem(label = "Subject", value = uiState.subject)
                    SummaryItem(label = "Category", value = uiState.category)
                    SummaryItem(label = "Location", value = uiState.location)
                    SummaryItem(label = "Description", value = uiState.description)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Submitted on: ${uiState.date}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }

        Button(
            onClick = onSendClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
//                .height(56.dp), // Fixed height
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // Background color
                contentColor = MaterialTheme.colorScheme.onPrimary   // Text/Icon color
            )
        ) {
            Text(
                text = "DONE",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SummaryItem(label: String, value: String) {
    Column {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = if (value.isBlank()) "Not provided" else value,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SummaryScreenPreview() {
    // Create a mock state to show in the preview
    val mockUiState = ReportUiState(
        subject = "Deep Pothole",
        category = "Pothole",
        location = "Jalan Reko, Bangi",
        description = "There is a very deep pothole near the traffic light that is dangerous for motorcyclists.",
        date = "Mon Oct 23"
    )

    MaterialTheme {
        Surface {
            SummaryScreen(uiState = mockUiState)
        }
    }
}