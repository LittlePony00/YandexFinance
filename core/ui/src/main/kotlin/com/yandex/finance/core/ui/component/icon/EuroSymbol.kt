package com.yandex.finance.core.ui.component.icon

import androidx.compose.foundation.Image
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
val Icons.EuroSymbol: ImageVector by lazy {
    ImageVector.Builder(
        name = "EuroSymbol",
        defaultWidth = 19.dp,
        defaultHeight = 19.dp,
        viewportWidth = 19f,
        viewportHeight = 19f
    ).apply {
        path(fill = SolidColor(Color(0xFF1D1B20))) {
            moveTo(13f, 16f)
            curveTo(10.5f, 16f, 8.32f, 14.58f, 7.24f, 12.5f)
            horizontalLineTo(13f)
            lineTo(14f, 10.5f)
            horizontalLineTo(6.58f)
            curveTo(6.53f, 10.17f, 6.5f, 9.84f, 6.5f, 9.5f)
            curveTo(6.5f, 9.16f, 6.53f, 8.83f, 6.58f, 8.5f)
            horizontalLineTo(13f)
            lineTo(14f, 6.5f)
            horizontalLineTo(7.24f)
            curveTo(7.788f, 5.445f, 8.614f, 4.561f, 9.63f, 3.944f)
            curveTo(10.646f, 3.326f, 11.811f, 3f, 13f, 3f)
            curveTo(14.61f, 3f, 16.09f, 3.59f, 17.23f, 4.57f)
            lineTo(19f, 2.8f)
            curveTo(17.353f, 1.318f, 15.216f, 0.498f, 13f, 0.5f)
            curveTo(9.08f, 0.5f, 5.76f, 3f, 4.5f, 6.5f)
            horizontalLineTo(1f)
            lineTo(0f, 8.5f)
            horizontalLineTo(4.06f)
            curveTo(4f, 8.83f, 4f, 9.16f, 4f, 9.5f)
            curveTo(4f, 9.84f, 4f, 10.17f, 4.06f, 10.5f)
            horizontalLineTo(1f)
            lineTo(0f, 12.5f)
            horizontalLineTo(4.5f)
            curveTo(5.76f, 16f, 9.08f, 18.5f, 13f, 18.5f)
            curveTo(15.31f, 18.5f, 17.41f, 17.63f, 19f, 16.2f)
            lineTo(17.22f, 14.43f)
            curveTo(16.09f, 15.41f, 14.62f, 16f, 13f, 16f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun EuroSymbolIconPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.EuroSymbol,
                contentDescription = null
            )
        }
    }
}
