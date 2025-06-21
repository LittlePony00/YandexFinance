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
val Icons.Settings: ImageVector by lazy {
    ImageVector.Builder(
        name = "Settings",
        defaultWidth = 21.dp,
        defaultHeight = 21.dp,
        viewportWidth = 21f,
        viewportHeight = 21f
    ).apply {
        path(fill = SolidColor(Color(0xFF2AE881))) {
            moveTo(11f, 3f)
            lineTo(17.477f, 6.75f)
            verticalLineTo(14.25f)
            lineTo(11f, 18f)
            lineTo(4.523f, 14.25f)
            verticalLineTo(6.75f)
            lineTo(11f, 3f)
            close()
            moveTo(11f, 12.545f)
            curveTo(11.542f, 12.545f, 12.063f, 12.33f, 12.446f, 11.946f)
            curveTo(12.83f, 11.563f, 13.045f, 11.042f, 13.045f, 10.5f)
            curveTo(13.045f, 9.958f, 12.83f, 9.437f, 12.446f, 9.054f)
            curveTo(12.063f, 8.67f, 11.542f, 8.455f, 11f, 8.455f)
            curveTo(10.458f, 8.455f, 9.937f, 8.67f, 9.554f, 9.054f)
            curveTo(9.17f, 9.437f, 8.955f, 9.958f, 8.955f, 10.5f)
            curveTo(8.955f, 11.042f, 9.17f, 11.563f, 9.554f, 11.946f)
            curveTo(9.937f, 12.33f, 10.458f, 12.545f, 11f, 12.545f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun SettingsIconPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Settings,
                contentDescription = null
            )
        }
    }
}
