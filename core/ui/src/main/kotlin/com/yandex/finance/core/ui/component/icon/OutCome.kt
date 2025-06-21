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
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.theme.YandexFinanceTheme

@Stable
val Icons.Outcome: ImageVector by lazy {
    ImageVector.Builder(
        name = "Outcome",
        defaultWidth = 21.dp,
        defaultHeight = 21.dp,
        viewportWidth = 21f,
        viewportHeight = 21f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.EvenOdd
        ) {
            path(fill = SolidColor(Color(0xFF2AE881))) {
                moveTo(18.254f, 16.446f)
                horizontalLineTo(16.593f)
                verticalLineTo(11.461f)
                horizontalLineTo(13.269f)
                verticalLineTo(16.446f)
                horizontalLineTo(12.162f)
                verticalLineTo(9.799f)
                horizontalLineTo(8.838f)
                verticalLineTo(16.446f)
                horizontalLineTo(7.73f)
                verticalLineTo(8.137f)
                horizontalLineTo(4.407f)
                verticalLineTo(16.446f)
                horizontalLineTo(2.745f)
                verticalLineTo(17f)
                horizontalLineTo(18.254f)
                verticalLineTo(16.446f)
                close()
            }
            path(fill = SolidColor(Color(0xFF2AE881))) {
                moveTo(13.707f, 10.17f)
                lineTo(16.787f, 8.902f)
                lineTo(15.302f, 5.928f)
                lineTo(14.804f, 6.171f)
                lineTo(15.84f, 8.254f)
                lineTo(4.507f, 4f)
                lineTo(4.307f, 4.521f)
                lineTo(15.646f, 8.775f)
                lineTo(13.497f, 9.655f)
                lineTo(13.707f, 10.17f)
                close()
            }
        }
    }.build()
}

@Preview
@Composable
private fun OutcomeIconPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Outcome,
                contentDescription = null
            )
        }
    }
}
