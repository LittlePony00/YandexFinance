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
val Icons.Confirm: ImageVector by lazy {
    ImageVector.Builder(
        name = "Icon",
        defaultWidth = 18.dp,
        defaultHeight = 13.dp,
        viewportWidth = 18f,
        viewportHeight = 13f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(6.55f, 13f)
            lineTo(0.85f, 7.3f)
            lineTo(2.275f, 5.875f)
            lineTo(6.55f, 10.15f)
            lineTo(15.725f, 0.975f)
            lineTo(17.15f, 2.4f)
            lineTo(6.55f, 13f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun ConfirmIconPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Confirm,
                contentDescription = null
            )
        }
    }
}
