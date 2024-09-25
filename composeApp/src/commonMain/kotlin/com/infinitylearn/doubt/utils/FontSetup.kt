package com.infinitylearn.doubt.utils

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import doubts.composeapp.generated.resources.Res
import doubts.composeapp.generated.resources.montserrat_bold
import doubts.composeapp.generated.resources.montserrat_italic
import doubts.composeapp.generated.resources.montserrat_light
import doubts.composeapp.generated.resources.montserrat_medium
import doubts.composeapp.generated.resources.montserrat_regular
import doubts.composeapp.generated.resources.montserrat_semi_bold
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppFontFamily() = FontFamily(


    Font(Res.font.montserrat_bold, FontWeight.Bold),
    Font(Res.font.montserrat_regular, FontWeight.Normal),
    Font(Res.font.montserrat_italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.montserrat_light, FontWeight.Light),
    Font(Res.font.montserrat_medium, FontWeight.Medium),
    Font(Res.font.montserrat_semi_bold, FontWeight.SemiBold)
)

@Composable
fun AppTypography() = Typography().run {

    val fontFamily = AppFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}