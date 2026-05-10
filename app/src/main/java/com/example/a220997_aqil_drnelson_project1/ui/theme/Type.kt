package com.example.a220997_aqil_drnelson_project1.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.a220997_aqil_drnelson_project1.R

val Mozilla = FontFamily(
    Font(R.font.mozillatext_medium,)
)


val Roboto1 = FontFamily(
    Font(R.font.roboto_bold)
)

val Roboto2 = FontFamily(
    Font(R.font.roboto_semibold)
)
val AppTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = Mozilla,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Roboto1,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Roboto2,
        fontSize = 15.sp
    )

)
