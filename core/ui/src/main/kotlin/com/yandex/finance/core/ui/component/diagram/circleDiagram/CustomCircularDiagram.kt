package com.yandex.finance.core.ui.component.diagram.circleDiagram

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ortin.ortinFyForAuthors.core.ui.components.diagram.circleDiagram.diagramColorList
import com.yandex.finance.core.ui.theme.YandexFinanceTheme

@Composable
fun CustomCircularDiagram(
    modifier: Modifier = Modifier,
    totalValue: Int = 100,
    isPercent: Boolean = true,
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    isBackEnabled: Boolean? = null,
    isNextEnabled: Boolean? = null,
    colorText: Color = MaterialTheme.colorScheme.onSurface,
    colorsList: List<Color> = diagramColorList,
    diagramInfoList: List<UiDiagramInfo> = listOf(),
) {
    val spaceBelowDiagram = 32.dp

    val sortedProjectList = diagramInfoList.sortedBy { project -> project.name }

    var selectedProject by rememberSaveable(
        diagramInfoList,
        stateSaver = ProjectSaver
    ) { mutableStateOf(null) }
    var selectedColor by rememberSaveable(
        diagramInfoList,
        stateSaver = ColorSaver
    ) { mutableStateOf(null) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(spaceBelowDiagram, Alignment.Top),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            isBackEnabled?.let { enabled ->
                DiagramIconButton(
                    onClick = onBackClick,
                    enabled = enabled,
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    modifier = Modifier.size(40.dp)
                )
            }
            Box(contentAlignment = Alignment.Center) {
                DiagramCanvas(
                    modifier = Modifier.size(184.dp),
                    totalValue = totalValue,
                    colorList = selectedColor?.let { listOf(it) } ?: colorsList,
                    projectList = selectedProject?.let { listOf(it) } ?: sortedProjectList
                )
                DiagramText(
                    isPercent = isPercent,
                    totalValue = totalValue,
                    diagramInfoList = sortedProjectList,
                    color = colorText,
                    selectedValue = selectedProject?.let { value -> value.value } ?: 0,
                )
            }
            isNextEnabled?.let { enabled ->
                DiagramIconButton(
                    onClick = onNextClick,
                    enabled = enabled,
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    modifier = Modifier
                        .size(40.dp)
                        .rotate(180f)
                )
            }
        }
        ProjectList(
            projectList = sortedProjectList,
            colorList = colorsList,
            colorText = colorText,
            onClick = { project, color ->
                if (selectedProject == null || selectedProject != project) {
                    selectedProject = project
                    selectedColor = color
                } else {
                    selectedProject = null
                    selectedColor = null
                }
            }
        )
    }
}

@Composable
@Preview
fun CustomCircularDiagramPreview() {
    YandexFinanceTheme {
        val list = listOf(
            UiDiagramInfo(
                name = "М",
                value = 526
            ),
            UiDiagramInfo(
                name = "Ж",
                value = 327
            )
        )

        CustomCircularDiagram(
            modifier = Modifier.padding(top = 250.dp),
            diagramInfoList = list,
            colorsList = listOf(Color.Magenta.copy(alpha = .7f), Color.Blue.copy(alpha = .7f)),
            totalValue = list.sumOf { it.value },
            onBackClick = { /* Do nothing */ },
            onNextClick = { /* Do nothing */ },
            isBackEnabled = null,
            isNextEnabled = null
        )
    }
}

private val ProjectSaver = listSaver<UiDiagramInfo?, Any?>(
    save = { it?.let { listOf(it.value, it.name) } ?: listOf(null) },
    restore = { if (it[0] != null) UiDiagramInfo(it[0] as Int, it[1] as String?) else null }
)

private val ColorSaver = listSaver<Color?, Any?>(
    save = { it?.let { listOf(it.value.toLong()) } ?: listOf(null) },
    restore = { if (it[0] != null) Color((it[0] as Long).toULong()) else null }
)
