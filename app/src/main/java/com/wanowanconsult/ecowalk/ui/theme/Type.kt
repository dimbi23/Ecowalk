package com.wanowanconsult.ecowalk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.wanowanconsult.ecowalk.R

val plusJakartaSans = FontFamily(
    Font(R.font.plusjakartasans_regular, weight = FontWeight.Normal),
    Font(R.font.plusjakartasans_medium, weight = FontWeight.Medium),
    Font(R.font.plusjakartasans_semibold, weight = FontWeight.SemiBold),
    Font(R.font.plusjakartasans_bold, weight = FontWeight.Bold),
    Font(R.font.plusjakartasans_extrabold, weight = FontWeight.ExtraBold),
)

val Typography = Typography(
     bodyMedium = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    displayLarge = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.ExtraBold
    ),
    headlineLarge = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.ExtraBold
    ),
    headlineMedium = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.Medium
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)