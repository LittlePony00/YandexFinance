package com.yandex.finance.core.ui.component.diagram.circleDiagram

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ortin.ortinFyForAuthors.core.ui.components.diagram.circleDiagram.diagramColorList
import kotlin.math.min

@Composable
internal fun DiagramCanvas(
    totalValue: Int,
    colorList: List<Color>,
    projectList: List<UiDiagramInfo>,
    modifier: Modifier = Modifier,
    maxRadius: Dp = 124.dp,
    strokeWidth: Dp = 12.dp
) {
    val animateArc = remember(projectList) { Animatable(initialValue = 0f) }

    LaunchedEffect(projectList) {
        animateArc.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = min(maxRadius.toPx(), min(canvasWidth, canvasHeight) / 2f - strokeWidth.toPx() / 2f)

        val center = Offset(canvasWidth / 2f, canvasHeight / 2f)

        drawCircle(
            radius = radius,
            center = center,
            color = Color(0xFFF2F4FC),
            style = Stroke(width = strokeWidth.toPx()),
        )

        val totalHours = projectList.sumOf { it.value }
        val index = if (totalHours <= totalValue) 360f / totalValue else 360f / totalHours
        var currentAngle: Float = -90f

        val arcSize = Size(radius * 2, radius * 2)

        val arcTopLeft = Offset(
            center.x - radius,
            center.y - radius
        )

        projectList.forEachIndexed { i, project ->
            val color = colorList[i % colorList.size]
            val sweepAngle = index * project.value * animateArc.value

            drawArc(
                color = color,
                startAngle = currentAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                size = arcSize,
                topLeft = arcTopLeft,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Butt
                )
            )

            currentAngle += sweepAngle
        }
    }
}

@Preview
@Composable
fun CircularDiagramProjectPreview() {
    val projectList = listOf(
        UiDiagramInfo(name = "Project 1", value = 10),
        UiDiagramInfo(name = "Project 2", value = 20),
        UiDiagramInfo(name = "Project 3", value = 15),
        UiDiagramInfo(name = "Project 4", value = 10),
        UiDiagramInfo(name = "Project 5", value = 20),
        UiDiagramInfo(name = "Project 6", value = 15),
        UiDiagramInfo(name = "Другое", value = 10),
        UiDiagramInfo(name = "Отсутствия", value = 5)
    )


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DiagramIconButton(
                onClick = { /* Do nothing */ },
                icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                modifier = Modifier.size(24.dp)
            )
            Box(contentAlignment = Alignment.Center) {
                DiagramCanvas(
                    modifier = Modifier.size(184.dp),
                    totalValue = 160,
                    colorList = diagramColorList,
                    projectList = projectList
                )
                DiagramText(
                    selectedValue = 0,
                    color = Color.White,
                    diagramInfoList = projectList
                )
            }
            DiagramIconButton(
                onClick = { /* Do nothing */ },
                icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                modifier = Modifier.size(24.dp)
            )
        }
        ProjectList(
            projectList = projectList,
            colorList = diagramColorList,
            colorText = Color.White,
            onClick = { _, _ -> /* Do nothing */ }
        )
    }
}
