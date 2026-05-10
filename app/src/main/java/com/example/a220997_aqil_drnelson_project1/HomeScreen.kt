package com.example.a220997_aqil_drnelson_project1

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FeatureItem(iconRes: Int, label: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 80.dp, height = 90.dp)
            .clickable(
                onClick = { /* Handle click here, e.g., navigate to a new screen */ }
            ),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(45.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 1 // Keeps the grid neat
        )
    }
}

@Composable
fun ButtonFeatures(modifier: Modifier = Modifier){

//    var isExpanded by remember { mutableStateOf(false) }

    val features = listOf(
        Pair(R.drawable.nearby, "Nearby"),
        Pair(R.drawable.disaster, "Bencana"),
        Pair(R.drawable.shade, "Teduhan"),
        Pair(R.drawable.sos, "SOS Hotline"),
        Pair(R.drawable.cctv, "CCTV"),
        Pair(R.drawable.news, "Berita"),
        Pair(R.drawable.hot, "HOTS"),
        Pair(R.drawable.plus, "PLUS")
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
//            .animateContentSize(
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioLowBouncy,
//                    stiffness = Spring.StiffnessLow
//                )
//            )
//            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
//                .animateContentSize(
//                    animationSpec = spring(
//                        dampingRatio = Spring.DampingRatioLowBouncy,
//                        stiffness = Spring.StiffnessLow
//                    )
//                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            val displayItems = if (isExpanded) features else features.take(3)

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                features.chunked(3).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowItems.forEach { (iconRes, label) ->
                            FeatureItem(iconRes, label)
                        }
                    }
                }
            }
//            Icon(
//                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                contentDescription = null,
//                tint = MaterialTheme.colorScheme.outlineVariant,
//                modifier = Modifier.padding(top = 8.dp)
//            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
//    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
){
    var nameInput by remember { mutableStateOf("") }
    var passInput by remember { mutableStateOf("") }

    // This variable actually controls what the Navbar shows
    var confirmedName by remember { mutableStateOf("Guest") }

    val scrollState = rememberScrollState()
    Scaffold(
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {

//            loginForm(
//                modifier = Modifier.padding(all = 30.dp),
//                nameInput = nameInput,
//                passInput = passInput,
//                onNameChanged = { nameInput = it },
//                onPassChanged = { passInput = it },
//                onLoginClick = {
//                    // Logic: Update confirmedName with input, or default to Guest if blank
//                    confirmedName = nameInput.ifBlank { "Guest" }
//                }
//
//            )

            ButtonFeatures(
                modifier = Modifier.padding(all = 30.dp)
            )
//        AduanButton(
//            modifier = Modifier.padding(horizontal = 30.dp),
//            onClick = onNextButtonClicked
//        )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    // You can wrap this in your AppTheme if you have one

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        HomeScreen()
    }
}