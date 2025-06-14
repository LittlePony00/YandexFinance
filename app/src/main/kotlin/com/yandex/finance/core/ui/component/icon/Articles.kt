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
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.theme.YandexFinanceTheme

@Stable
val Icons.Articles: ImageVector by lazy {
    ImageVector.Builder(
        name = "Articles",
        defaultWidth = 21.dp,
        defaultHeight = 21.dp,
        viewportWidth = 21f,
        viewportHeight = 21f
    ).apply {
        group(
            clipPathData = PathData {
                moveTo(4.741f, 4f)
                horizontalLineToRelative(12.519f)
                verticalLineToRelative(13f)
                horizontalLineToRelative(-12.519f)
                close()
            }
        ) {
            path(fill = SolidColor(Color(0xFF2AE881))) {
                moveTo(5.222f, 16.518f)
                horizontalLineTo(17.259f)
                verticalLineTo(13.148f)
                horizontalLineTo(5.222f)
                verticalLineTo(12.185f)
                horizontalLineTo(14.37f)
                verticalLineTo(8.815f)
                horizontalLineTo(5.222f)
                verticalLineTo(7.852f)
                horizontalLineTo(11.963f)
                verticalLineTo(4.481f)
                horizontalLineTo(5.222f)
                verticalLineTo(4f)
                horizontalLineTo(4.741f)
                verticalLineTo(17f)
                horizontalLineTo(5.222f)
                verticalLineTo(16.518f)
                close()
            }
        }
    }.build()
}

@Preview
@Composable
private fun ArticlesIconPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Articles,
                contentDescription = null
            )
        }
    }
}
