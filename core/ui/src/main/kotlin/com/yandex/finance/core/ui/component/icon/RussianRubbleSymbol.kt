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
val Icons.RussianRubleSymbol: ImageVector by lazy {
    ImageVector.Builder(
        name = "RussianRubleSymbol",
        defaultWidth = 14.dp,
        defaultHeight = 19.dp,
        viewportWidth = 14f,
        viewportHeight = 19f
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(8.5f, 0.5f)
            horizontalLineTo(2f)
            verticalLineTo(9.5f)
            horizontalLineTo(0f)
            verticalLineTo(11.5f)
            horizontalLineTo(2f)
            verticalLineTo(13.5f)
            horizontalLineTo(0f)
            verticalLineTo(15.5f)
            horizontalLineTo(2f)
            verticalLineTo(18.5f)
            horizontalLineTo(4f)
            verticalLineTo(15.5f)
            horizontalLineTo(8f)
            verticalLineTo(13.5f)
            horizontalLineTo(4f)
            verticalLineTo(11.5f)
            horizontalLineTo(8.5f)
            curveTo(11.54f, 11.5f, 14f, 9.04f, 14f, 6f)
            curveTo(14f, 2.96f, 11.54f, 0.5f, 8.5f, 0.5f)
            close()
            moveTo(8.5f, 9.5f)
            horizontalLineTo(4f)
            verticalLineTo(2.5f)
            horizontalLineTo(8.5f)
            curveTo(10.43f, 2.5f, 12f, 4.07f, 12f, 6f)
            curveTo(12f, 7.93f, 10.43f, 9.5f, 8.5f, 9.5f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun RussianRubleSymbolIconPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.RussianRubleSymbol,
                contentDescription = null
            )
        }
    }
}
