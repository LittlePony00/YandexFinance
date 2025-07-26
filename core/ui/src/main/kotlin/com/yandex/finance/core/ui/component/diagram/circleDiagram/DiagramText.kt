package com.yandex.finance.core.ui.component.diagram.circleDiagram

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString

@Composable
internal fun DiagramText(
    color: Color,
    selectedValue: Int,
    diagramInfoList: List<UiDiagramInfo>,
    modifier: Modifier = Modifier,
    totalValue: Int = 100,
    isPercent: Boolean = true
) {
    val sum = diagramInfoList.sumOf { it.value }
    val textToShow = buildAnnotatedString {
        if (isPercent) {
            append(
                if (selectedValue == 0) {
                    "$sum"
                } else {
                    "$selectedValue%"
                }
            )
        } else {
            append("${if (selectedValue == 0) sum else selectedValue}")
            append(" / $totalValue")
        }
    }

    AnimatedDiagramText(
        modifier = modifier,
        text = textToShow,
        color = color,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Immutable
data class UiDiagramInfo(
    val value: Int,
    val name: String?
)

@Stable
fun DiagramInfo.asUiProjectInfo(): UiDiagramInfo =
    UiDiagramInfo(
        value = this.value,
        name = this.name
    )

data class DiagramInfo(
    val value: Int,
    val name: String?
)
