package com.yandex.finance.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yandex.finance.core.ui.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val YandexFinanceAppRobotoFamily = FontFamily(
    Font(R.font.roboto)
)

private val RobotoStyle = TextStyle(
    fontFamily = YandexFinanceAppRobotoFamily,
    fontWeight = FontWeight.Normal,
    lineHeight = 24.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val RobotoTitleLargeStyle = RobotoStyle.copy(
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp,
    fontWeight = FontWeight.Normal
)

val RobotoBodyLargeStyle = RobotoStyle.copy(
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = .5.sp,
    fontWeight = FontWeight.Normal
)

val RobotoBodyMediumStyle = RobotoStyle.copy(
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = .25.sp,
    fontWeight = FontWeight.Normal,
)

val RobotoLabelLargeStyle = RobotoStyle.copy(
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = .1.sp,
    fontWeight = FontWeight.Medium
)

val RobotoLabelMediumStyle = RobotoStyle.copy(
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = .5.sp,
    fontWeight = FontWeight.Medium
)
