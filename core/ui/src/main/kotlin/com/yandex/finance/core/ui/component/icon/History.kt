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
val Icons.History: ImageVector by lazy {
    ImageVector.Builder(
        name = "History",
        defaultWidth = 21.dp,
        defaultHeight = 18.dp,
        viewportWidth = 21f,
        viewportHeight = 18f
    ).apply {
        path(fill = SolidColor(Color(0xFF49454F))) {
            moveTo(12.5f, 5f)
            horizontalLineTo(11f)
            verticalLineTo(10f)
            lineTo(15.28f, 12.54f)
            lineTo(16f, 11.33f)
            lineTo(12.5f, 9.25f)
            verticalLineTo(5f)
            close()
            moveTo(12f, 0f)
            curveTo(9.613f, 0f, 7.324f, 0.948f, 5.636f, 2.636f)
            curveTo(3.948f, 4.324f, 3f, 6.613f, 3f, 9f)
            horizontalLineTo(0f)
            lineTo(3.96f, 13.03f)
            lineTo(8f, 9f)
            horizontalLineTo(5f)
            curveTo(5f, 7.143f, 5.738f, 5.363f, 7.05f, 4.05f)
            curveTo(8.363f, 2.737f, 10.144f, 2f, 12f, 2f)
            curveTo(13.856f, 2f, 15.637f, 2.737f, 16.95f, 4.05f)
            curveTo(18.263f, 5.363f, 19f, 7.143f, 19f, 9f)
            curveTo(19f, 10.856f, 18.263f, 12.637f, 16.95f, 13.95f)
            curveTo(15.637f, 15.262f, 13.856f, 16f, 12f, 16f)
            curveTo(10.07f, 16f, 8.32f, 15.21f, 7.06f, 13.94f)
            lineTo(5.64f, 15.36f)
            curveTo(6.472f, 16.2f, 7.462f, 16.867f, 8.554f, 17.32f)
            curveTo(9.646f, 17.773f, 10.818f, 18.004f, 12f, 18f)
            curveTo(14.387f, 18f, 16.676f, 17.052f, 18.364f, 15.364f)
            curveTo(20.052f, 13.676f, 21f, 11.387f, 21f, 9f)
            curveTo(21f, 6.613f, 20.052f, 4.324f, 18.364f, 2.636f)
            curveTo(16.676f, 0.948f, 14.387f, 0f, 12f, 0f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun HistoryIconPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.History,
                contentDescription = null
            )
        }
    }
}
