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
val Icons.DollarSymbol: ImageVector by lazy {
    ImageVector.Builder(
        name = "DollarSymbol",
        defaultWidth = 10.dp,
        defaultHeight = 19.dp,
        viewportWidth = 10f,
        viewportHeight = 19f
    ).apply {
        path(fill = SolidColor(Color(0xFF1D1B20))) {
            moveTo(0f, 12.5f)
            horizontalLineTo(2f)
            curveTo(2f, 13.58f, 3.37f, 14.5f, 5f, 14.5f)
            curveTo(6.63f, 14.5f, 8f, 13.58f, 8f, 12.5f)
            curveTo(8f, 11.4f, 6.96f, 11f, 4.76f, 10.47f)
            curveTo(2.64f, 9.94f, 0f, 9.28f, 0f, 6.5f)
            curveTo(0f, 4.71f, 1.47f, 3.19f, 3.5f, 2.68f)
            verticalLineTo(0.5f)
            horizontalLineTo(6.5f)
            verticalLineTo(2.68f)
            curveTo(8.53f, 3.19f, 10f, 4.71f, 10f, 6.5f)
            horizontalLineTo(8f)
            curveTo(8f, 5.42f, 6.63f, 4.5f, 5f, 4.5f)
            curveTo(3.37f, 4.5f, 2f, 5.42f, 2f, 6.5f)
            curveTo(2f, 7.6f, 3.04f, 8f, 5.24f, 8.53f)
            curveTo(7.36f, 9.06f, 10f, 9.72f, 10f, 12.5f)
            curveTo(10f, 14.29f, 8.53f, 15.81f, 6.5f, 16.32f)
            verticalLineTo(18.5f)
            horizontalLineTo(3.5f)
            verticalLineTo(16.32f)
            curveTo(1.47f, 15.81f, 0f, 14.29f, 0f, 12.5f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun DollarSymbolIconPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.DollarSymbol,
                contentDescription = null
            )
        }
    }
}
