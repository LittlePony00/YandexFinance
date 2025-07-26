package com.ortin.ortinFyForAuthors.core.ui.components.diagram.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.component.diagram.circleDiagram.UiDiagramInfo
import com.yandex.finance.core.ui.util.SpacerWidth

@Composable
fun DetailedInformation(
    offset: Offset,
    colorText: Color,
    data: UiDiagramInfo,
    constraints: Constraints,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Column(
        modifier = modifier
            .graphicsLayer {
                translationY = offset.y.coerceIn(
                    minimumValue = 0f,
                    maximumValue = constraints.maxHeight - size.height
                )
                translationX = offset.x.coerceIn(
                    minimumValue = 0f,
                    maximumValue = constraints.maxWidth - size.width
                )
            }
            .background(
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = data.name ?: "",
            color = colorText,
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .background(color = color, shape = CircleShape)
            )
            SpacerWidth(width = 8.dp)
            Text(
                text = "${data.value}",
                color = colorText
            )
        }
    }
}
