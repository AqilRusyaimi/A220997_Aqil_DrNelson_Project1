package com.example.a220997_aqil_drnelson_project1

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.* // This provides access to 'getValue' and 'setValue'
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Crucial imports for the "by" delegate to work
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class) // Required for ExposedDropdownMenuBox
@Composable
fun ReportForm(
    modifier: Modifier = Modifier,
    onCancelButtonClicked: () -> Unit = {},
    onSubmitButtonClicked: (String, String, String, String) -> Unit = { _, _, _, _ -> },
) {
    // State for form fields
    var subject by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Select Category") }
    var isExpanded by remember { mutableStateOf(false) }

    val categories = listOf("Pothole", "Street Light", "Drainage", "Others")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Submit New Report",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // Subject Field - FIXED
        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it }, // Directly assign the input 'it'
            label = { Text("Subject") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Category Dropdown
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                categories.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            category = item
                            isExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = location,
            onValueChange = { location = it }, // Directly assign the input 'it'
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Description Field
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description of issue") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            maxLines = 5
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onCancelButtonClicked,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer, // Background color
                    contentColor = MaterialTheme.colorScheme.onErrorContainer, // Text/Icon color
                )
            ) {
                Text(stringResource(R.string.cancel))
            }
            // Submit Button
            Button(
                onClick = { onSubmitButtonClicked(subject, category, description, location) },
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Background color
                    contentColor = MaterialTheme.colorScheme.onPrimary   // Text/Icon color
                ),
                enabled = subject.isNotBlank() &&
                        description.isNotBlank() &&
                        category != "Select Category"
            ) {
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.submit))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReportFormPreview() {
    // You can wrap this in your AppTheme if you have one
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ReportForm(

        )
    }
}