package com.yandex.finance.core.ui.component.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.theme.YandexFinanceTheme

@Stable
val Icons.Analys: ImageVector by lazy {
    materialIcon(name = "Analys") {
        materialPath {
            moveTo(18f, 10.11f)
            verticalLineTo(4f)
            curveTo(18f, 3.47f, 17.789f, 2.961f, 17.414f, 2.586f)
            curveTo(17.039f, 2.211f, 16.53f, 2f, 16f, 2f)
            horizontalLineTo(11.82f)
            curveTo(11.4f, 0.84f, 10.3f, 0f, 9f, 0f)
            curveTo(7.7f, 0f, 6.6f, 0.84f, 6.18f, 2f)
            horizontalLineTo(2f)
            curveTo(0.9f, 2f, 0f, 2.9f, 0f, 4f)
            verticalLineTo(18f)
            curveTo(0f, 18.53f, 0.211f, 19.039f, 0.586f, 19.414f)
            curveTo(0.961f, 19.789f, 1.47f, 20f, 2f, 20f)
            horizontalLineTo(8.11f)
            curveTo(9.37f, 21.24f, 11.09f, 22f, 13f, 22f)
            curveTo(16.87f, 22f, 20f, 18.87f, 20f, 15f)
            curveTo(20f, 13.09f, 19.24f, 11.37f, 18f, 10.11f)
            close()
            moveTo(9f, 2f)
            curveTo(9.55f, 2f, 10f, 2.45f, 10f, 3f)
            curveTo(10f, 3.55f, 9.55f, 4f, 9f, 4f)
            curveTo(8.45f, 4f, 8f, 3.55f, 8f, 3f)
            curveTo(8f, 2.45f, 8.45f, 2f, 9f, 2f)
            close()
            moveTo(2f, 18f)
            verticalLineTo(4f)
            horizontalLineTo(4f)
            verticalLineTo(6f)
            horizontalLineTo(14f)
            verticalLineTo(4f)
            horizontalLineTo(16f)
            verticalLineTo(8.68f)
            curveTo(15.09f, 8.25f, 14.08f, 8f, 13f, 8f)
            horizontalLineTo(4f)
            verticalLineTo(10f)
            horizontalLineTo(8.1f)
            curveTo(7.5f, 10.57f, 7.04f, 11.25f, 6.68f, 12f)
            horizontalLineTo(4f)
            verticalLineTo(14f)
            horizontalLineTo(6.08f)
            curveTo(6.03f, 14.33f, 6f, 14.66f, 6f, 15f)
            curveTo(6f, 16.08f, 6.25f, 17.09f, 6.68f, 18f)
            horizontalLineTo(2f)
            close()
            moveTo(13f, 20f)
            curveTo(10.24f, 20f, 8f, 17.76f, 8f, 15f)
            curveTo(8f, 12.24f, 10.24f, 10f, 13f, 10f)
            curveTo(15.76f, 10f, 18f, 12.24f, 18f, 15f)
            curveTo(18f, 17.76f, 15.76f, 20f, 13f, 20f)
            close()
            moveTo(13.5f, 15.25f)
            lineTo(16.36f, 16.94f)
            lineTo(15.61f, 18.16f)
            lineTo(12f, 16f)
            verticalLineTo(11f)
            horizontalLineTo(13.5f)
            verticalLineTo(15.25f)
            close()
        }
    }
}

@Preview
@Composable
private fun AnalysIconPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Analys,
                contentDescription = null
            )
        }
    }
}
