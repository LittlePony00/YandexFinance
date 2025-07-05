package com.yandex.finance.core.ui.component.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.theme.YandexFinanceTheme

@Stable
val Icons.Cancel: ImageVector by lazy {
    ImageVector.Builder(
        name = "Cancel",
        defaultWidth = 20.dp,
        defaultHeight = 21.dp,
        viewportWidth = 20f,
        viewportHeight = 21f
    ).apply {
        path(fill = SolidColor(Color(0xFFFFFFFF))) {
            moveTo(6.4f, 15.5f)
            lineTo(10f, 11.9f)
            lineTo(13.6f, 15.5f)
            lineTo(15f, 14.1f)
            lineTo(11.4f, 10.5f)
            lineTo(15f, 6.9f)
            lineTo(13.6f, 5.5f)
            lineTo(10f, 9.1f)
            lineTo(6.4f, 5.5f)
            lineTo(5f, 6.9f)
            lineTo(8.6f, 10.5f)
            lineTo(5f, 14.1f)
            lineTo(6.4f, 15.5f)
            close()
            moveTo(10f, 20.5f)
            curveTo(8.617f, 20.5f, 7.317f, 20.237f, 6.1f, 19.712f)
            curveTo(4.883f, 19.188f, 3.825f, 18.475f, 2.925f, 17.575f)
            curveTo(2.025f, 16.675f, 1.313f, 15.617f, 0.788f, 14.4f)
            curveTo(0.262f, 13.183f, 0f, 11.883f, 0f, 10.5f)
            curveTo(0f, 9.117f, 0.262f, 7.817f, 0.788f, 6.6f)
            curveTo(1.313f, 5.383f, 2.025f, 4.325f, 2.925f, 3.425f)
            curveTo(3.825f, 2.525f, 4.883f, 1.813f, 6.1f, 1.288f)
            curveTo(7.317f, 0.762f, 8.617f, 0.5f, 10f, 0.5f)
            curveTo(11.383f, 0.5f, 12.683f, 0.762f, 13.9f, 1.288f)
            curveTo(15.117f, 1.813f, 16.175f, 2.525f, 17.075f, 3.425f)
            curveTo(17.975f, 4.325f, 18.688f, 5.383f, 19.212f, 6.6f)
            curveTo(19.737f, 7.817f, 20f, 9.117f, 20f, 10.5f)
            curveTo(20f, 11.883f, 19.737f, 13.183f, 19.212f, 14.4f)
            curveTo(18.688f, 15.617f, 17.975f, 16.675f, 17.075f, 17.575f)
            curveTo(16.175f, 18.475f, 15.117f, 19.188f, 13.9f, 19.712f)
            curveTo(12.683f, 20.237f, 11.383f, 20.5f, 10f, 20.5f)
            close()
            moveTo(10f, 18.5f)
            curveTo(12.233f, 18.5f, 14.125f, 17.725f, 15.675f, 16.175f)
            curveTo(17.225f, 14.625f, 18f, 12.733f, 18f, 10.5f)
            curveTo(18f, 8.267f, 17.225f, 6.375f, 15.675f, 4.825f)
            curveTo(14.125f, 3.275f, 12.233f, 2.5f, 10f, 2.5f)
            curveTo(7.767f, 2.5f, 5.875f, 3.275f, 4.325f, 4.825f)
            curveTo(2.775f, 6.375f, 2f, 8.267f, 2f, 10.5f)
            curveTo(2f, 12.733f, 2.775f, 14.625f, 4.325f, 16.175f)
            curveTo(5.875f, 17.725f, 7.767f, 18.5f, 10f, 18.5f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun CancelIconPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Cancel,
                contentDescription = null,
            )
        }
    }
}
