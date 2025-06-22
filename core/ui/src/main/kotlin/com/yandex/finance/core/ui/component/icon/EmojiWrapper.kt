package com.yandex.finance.core.ui.component.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.theme.RobotoEmojiStyle

@Composable
fun EmojiWrapper(
    text: String,
    emoji: String?,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color = MaterialTheme.colorScheme.secondary
) {
    val first = text.getOrElse(0, defaultValue = { ' ' }).uppercase()
    val second = text.getOrElse(1, defaultValue = { ' ' }).uppercase()

    if (emoji == null) {
        Text(
            modifier = modifier
                .drawBehind {
                    drawCircle(
                        color = containerColor,
                        radius = this.size.minDimension
                    )
                },
            text = "$first$second",
            color = contentColor,
            style = RobotoEmojiStyle
        )
    } else {
        Box(
            modifier = modifier
                .size(24.dp)
                .background(color = containerColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji)
        }
    }
}
