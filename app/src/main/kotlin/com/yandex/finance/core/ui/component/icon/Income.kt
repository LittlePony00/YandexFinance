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
val Icons.Income: ImageVector by lazy {
    ImageVector.Builder(
        name = "Income",
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
                moveTo(17.593f, 16.493f)
                horizontalLineTo(16.073f)
                verticalLineTo(8.894f)
                horizontalLineTo(13.033f)
                verticalLineTo(16.493f)
                horizontalLineTo(12.02f)
                verticalLineTo(10.414f)
                horizontalLineTo(8.98f)
                verticalLineTo(16.493f)
                horizontalLineTo(7.967f)
                verticalLineTo(11.934f)
                horizontalLineTo(4.927f)
                verticalLineTo(16.493f)
                horizontalLineTo(3.407f)
                verticalLineTo(17f)
                horizontalLineTo(17.593f)
                verticalLineTo(16.493f)
                close()
            }
            path(fill = SolidColor(Color(0xFF2AE881))) {
                moveTo(14.892f, 7.876f)
                lineTo(16.25f, 5.155f)
                lineTo(13.433f, 4f)
                lineTo(13.241f, 4.466f)
                lineTo(15.207f, 5.277f)
                lineTo(4.836f, 9.163f)
                lineTo(5.018f, 9.639f)
                lineTo(15.389f, 5.748f)
                lineTo(14.436f, 7.653f)
                lineTo(14.892f, 7.876f)
                close()
            }
        }
    }.build()
}

@Preview
@Composable
private fun IncomeIconPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Income,
                contentDescription = null
            )
        }
    }
}
