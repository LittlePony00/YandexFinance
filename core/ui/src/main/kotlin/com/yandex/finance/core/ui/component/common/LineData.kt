package com.ortin.ortinFyForAuthors.core.ui.components.diagram.common

import androidx.compose.ui.graphics.Color
import com.yandex.finance.core.ui.component.diagram.circleDiagram.UiDiagramInfo

data class LineData(
    val name: String,
    val color: Color,
    val data: List<UiDiagramInfo>
)
