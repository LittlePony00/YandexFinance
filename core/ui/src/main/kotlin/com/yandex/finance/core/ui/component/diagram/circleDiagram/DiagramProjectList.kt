package com.yandex.finance.core.ui.component.diagram.circleDiagram

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ProjectItem(
    project: UiDiagramInfo,
    color: Color,
    colorText: Color,
    modifier: Modifier = Modifier,
    onProjectItemClick: (projectInfo: UiDiagramInfo, color: Color) -> Unit
) {
    Row(modifier = modifier) {
        AssistChip(
            onClick = {
                onProjectItemClick(
                    UiDiagramInfo(
                        value = project.value,
                        name = project.name
                    ),
                    color
                )
            },
            label = {
                Text(
                    text = project.name ?: "",
                    color = colorText
                )
            },
            leadingIcon = {
                Image(
                    painter = ColorPainter(color),
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                )
            },
            border = null
        )
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun ProjectList(
    projectList: List<UiDiagramInfo>,
    colorList: List<Color>,
    colorText: Color,
    modifier: Modifier = Modifier,
    onClick: (project: UiDiagramInfo, color: Color) -> Unit
) {
    AnimatedContent(
        targetState = projectList,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(720, delayMillis = 90)
            ).apply {
                plus(
                    scaleIn(
                        initialScale = 0.92f,
                        animationSpec = tween(durationMillis = 220, delayMillis = 90)
                    )
                )
            }.togetherWith(fadeOut(animationSpec = tween(90)))
        },
        label = ""
    ) { projectInfoList ->
        val hours = projectInfoList.let { projects ->
            projects.sumOf { project -> project.value }
        }

        if (hours == 0) {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = "No Data",
                color = colorText,
                textAlign = TextAlign.Center
            )
        } else {
            FlowRow(
                modifier = modifier.fillMaxWidth(),
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 24.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalArrangement = Arrangement.Top
            ) {
                projectInfoList.forEachIndexed { index, project ->
                    if (project.value != 0) {
                        ProjectItem(
                            project = project,
                            color = colorList[index % colorList.size],
                            colorText = colorText,
                            onProjectItemClick = { item, color ->
                                onClick(item, color)
                            }
                        )
                    }
                }
            }
        }
    }
}
