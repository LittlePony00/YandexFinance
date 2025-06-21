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
val Icons.Check: ImageVector by lazy {
    ImageVector.Builder(
        name = "Check",
        defaultWidth = 21.dp,
        defaultHeight = 21.dp,
        viewportWidth = 21f,
        viewportHeight = 21f
    ).apply {
        path(fill = SolidColor(Color(0xFF2AE881))) {
            moveTo(6.396f, 4f)
            curveTo(5.8f, 4f, 5.313f, 4.488f, 5.313f, 5.083f)
            verticalLineTo(15.917f)
            curveTo(5.313f, 16.513f, 5.8f, 17f, 6.396f, 17f)
            horizontalLineTo(15.604f)
            curveTo(16.2f, 17f, 16.688f, 16.513f, 16.688f, 15.917f)
            verticalLineTo(5.083f)
            curveTo(16.688f, 4.488f, 16.2f, 4f, 15.604f, 4f)
            horizontalLineTo(6.396f)
            close()
            moveTo(8.563f, 15.375f)
            horizontalLineTo(6.938f)
            verticalLineTo(13.75f)
            horizontalLineTo(8.563f)
            verticalLineTo(15.375f)
            close()
            moveTo(8.563f, 13.208f)
            horizontalLineTo(6.938f)
            verticalLineTo(11.583f)
            horizontalLineTo(8.563f)
            verticalLineTo(13.208f)
            close()
            moveTo(8.563f, 11.042f)
            horizontalLineTo(6.938f)
            verticalLineTo(9.417f)
            horizontalLineTo(8.563f)
            verticalLineTo(11.042f)
            close()
            moveTo(10.729f, 15.375f)
            horizontalLineTo(9.104f)
            verticalLineTo(13.75f)
            horizontalLineTo(10.729f)
            verticalLineTo(15.375f)
            close()
            moveTo(10.729f, 13.208f)
            horizontalLineTo(9.104f)
            verticalLineTo(11.583f)
            horizontalLineTo(10.729f)
            verticalLineTo(13.208f)
            close()
            moveTo(10.729f, 11.042f)
            horizontalLineTo(9.104f)
            verticalLineTo(9.417f)
            horizontalLineTo(10.729f)
            verticalLineTo(11.042f)
            close()
            moveTo(12.896f, 15.375f)
            horizontalLineTo(11.271f)
            verticalLineTo(13.75f)
            horizontalLineTo(12.896f)
            verticalLineTo(15.375f)
            close()
            moveTo(12.896f, 13.208f)
            horizontalLineTo(11.271f)
            verticalLineTo(11.583f)
            horizontalLineTo(12.896f)
            verticalLineTo(13.208f)
            close()
            moveTo(12.896f, 11.042f)
            horizontalLineTo(11.271f)
            verticalLineTo(9.417f)
            horizontalLineTo(12.896f)
            verticalLineTo(11.042f)
            close()
            moveTo(15.063f, 15.375f)
            horizontalLineTo(13.438f)
            verticalLineTo(11.583f)
            horizontalLineTo(15.063f)
            verticalLineTo(15.375f)
            close()
            moveTo(15.063f, 11.042f)
            horizontalLineTo(13.438f)
            verticalLineTo(9.417f)
            horizontalLineTo(15.063f)
            verticalLineTo(11.042f)
            close()
            moveTo(15.063f, 8.333f)
            horizontalLineTo(6.938f)
            verticalLineTo(5.625f)
            horizontalLineTo(15.063f)
            verticalLineTo(8.333f)
            close()
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
                imageVector = Icons.Check,
                contentDescription = null
            )
        }
    }
}
