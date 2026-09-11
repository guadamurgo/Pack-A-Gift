package com.packagift.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.packagift.app.R

/** Roboto (system default font on Android). */
val Roboto = FontFamily.Default

/** Ahsing is used only for the main "Pack-A-Gift" logo/title. */
val Ahsing = FontFamily(
    Font(R.font.ahsing, FontWeight.Normal)
)

private fun style(
    size: Int,
    weight: FontWeight = FontWeight.Normal,
    lineHeight: Int = (size * 1.35f).toInt(),
    family: FontFamily = Roboto
) = TextStyle(
    fontFamily = family,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = lineHeight.sp
)

val AppTypography = Typography(
    displayLarge = style(44, FontWeight.Bold),
    displayMedium = style(36, FontWeight.Bold),
    displaySmall = style(30, FontWeight.Bold),
    headlineLarge = style(26, FontWeight.Bold),
    headlineMedium = style(23, FontWeight.Bold),
    headlineSmall = style(20, FontWeight.Bold),
    titleLarge = style(19, FontWeight.Bold),
    titleMedium = style(17, FontWeight.Bold),
    titleSmall = style(15, FontWeight.Bold),
    bodyLarge = style(16),
    bodyMedium = style(14),
    bodySmall = style(12),
    labelLarge = style(15, FontWeight.Bold),
    labelMedium = style(13, FontWeight.Bold),
    labelSmall = style(11, FontWeight.Bold)
)
