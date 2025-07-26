package com.yandex.finance.core.ui.component.diagram.barDiagram

import android.graphics.Paint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.component.diagram.circleDiagram.UiDiagramInfo
import com.ortin.ortinFyForAuthors.core.ui.components.diagram.common.DetailedInformation
import com.ortin.ortinFyForAuthors.core.ui.components.diagram.common.formatNumber
import com.yandex.finance.core.ui.theme.YandexFinanceTheme
import kotlinx.coroutines.delay

@Composable
fun CustomHorizontalBarDiagram(
    upperValue: Int,
    data: List<UiDiagramInfo>,
    detailedInformationContent: @Composable BoxWithConstraintsScope.(offset: Offset, dataIndex: Int) -> Unit,
    modifier: Modifier = Modifier,
    barThickness: Float = 55f,
    textInfoSize: Dp = 12.dp,
    spaceFromBottom: Float = 35f,
    lineColor: Color = MaterialTheme.colorScheme.primary,
    barCornerRadius: Dp = 12.dp,
    strokeLineWidth: Float = 5f,
    textColor: Color = Color.White,
    shouldDrawLines: Boolean = true,
) {
    var dataIndex by remember { mutableIntStateOf(0) }
    var dataOffset by remember { mutableStateOf<Offset?>(null) }

    var isAnimationStarted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        isAnimationStarted = true
    }

    val animatedValues = data.mapIndexed { index, value ->
        val animatedValue by animateFloatAsState(
            targetValue = if (!isAnimationStarted) 0f else value.value.toFloat(),
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = index * 100
            ),
            label = ""
        )
        animatedValue
    }

    BoxWithConstraints(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val spacePerDataY = size.height / data.size

            if (shouldDrawLines) {
                for (i in 0..5) {
                    val x = size.width * (i / 5f)

                    drawLine(
                        strokeWidth = 1.dp.toPx(),
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                        color = lineColor.copy(alpha = .5f),
                    )
                }
            }

            animatedValues.forEachIndexed { index, value ->
                val barWidth = (value / upperValue) * size.width
                val y = spaceFromBottom + index * spacePerDataY
                val cornerRadius = barCornerRadius.toPx()

                val barPath = Path().apply {
                    moveTo(0f, y)
                    lineTo(barWidth - cornerRadius, y)
                    arcTo(
                        Rect(
                            left = barWidth - cornerRadius,
                            top = y,
                            right = barWidth,
                            bottom = y + cornerRadius
                        ),
                        270f,
                        90f,
                        false
                    )
                    lineTo(barWidth, y + barThickness - cornerRadius)
                    arcTo(
                        Rect(
                            left = barWidth - cornerRadius,
                            top = y + barThickness - cornerRadius,
                            right = barWidth,
                            bottom = y + barThickness
                        ),
                        0f,
                        90f,
                        false
                    )
                    lineTo(0f, y + barThickness)
                    close()
                }

                drawPath(
                    path = barPath,
                    color = lineColor
                )

                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        formatNumber(data[index].value),
                        barWidth + 8.dp.toPx(),
                        y + barThickness / 2 + textInfoSize.toPx() / 3,
                        Paint().apply {
                            color = textColor.toArgb()
                            textAlign = Paint.Align.LEFT
                            textSize = textInfoSize.toPx()
                        }
                    )
                }

                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        data[index].name ?: "",
                        -8.dp.toPx(),
                        y + barThickness / 2 + textInfoSize.toPx() / 3,
                        Paint().apply {
                            color = textColor.toArgb()
                            textAlign = Paint.Align.RIGHT
                            textSize = textInfoSize.toPx()
                        }
                    )
                }
            }

            if (shouldDrawLines) {
                drawLine(
                    color = lineColor,
                    strokeWidth = strokeLineWidth,
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = size.width, y = 0f),
                )

                drawLine(
                    color = lineColor,
                    strokeWidth = strokeLineWidth,
                    start = Offset(x = 0f, y = -2.5f),
                    end = Offset(x = 0f, y = size.height),
                )
            }
        }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        dataIndex =
                            (offset.y / ((size.height - barThickness + spaceFromBottom) / data.size))
                                .coerceIn(
                                    0f,
                                    data.lastIndex.toFloat()
                                )
                                .toInt()
                        dataOffset = offset
                    }
                }
        ) {
            dataOffset?.let { offset ->
                detailedInformationContent(offset, dataIndex)
            }
        }
    }
}

@Preview
@Composable
private fun CustomBarDiagramPreview() {
    YandexFinanceTheme  {
        val list = listOf(
            UiDiagramInfo(
                name = "January",
                value = 200
            ),
            UiDiagramInfo(
                name = "February",
                value = 343
            ),
            UiDiagramInfo(
                name = "March",
                value = 421
            ),
            UiDiagramInfo(
                name = "April",
                value = 534
            ),
            UiDiagramInfo(
                name = "May",
                value = 599
            ),
            UiDiagramInfo(
                name = "June",
                value = 570
            ),
            UiDiagramInfo(
                name = "July",
                value = 800
            ),
            UiDiagramInfo(
                name = "August",
                value = 1154
            ),
            UiDiagramInfo(
                name = "September",
                value = 1423
            ),
            UiDiagramInfo(
                name = "October",
                value = 1800
            ),
            UiDiagramInfo(
                name = "November",
                value = 1900
            ),
            UiDiagramInfo(
                name = "December",
                value = 2321
            ),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp, vertical = 24.dp)
        ) {
            CustomHorizontalBarDiagram(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                data = list.map { data ->
                    data.copy(name = data.name?.slice(0..2))

                },
                barCornerRadius = 8.dp,
                upperValue = list.maxOfOrNull { it.value } ?: 0,
                detailedInformationContent = { offset, dataIndex ->
                    DetailedInformation(
                        offset = offset,
                        constraints = this@CustomHorizontalBarDiagram.constraints,
                        data = list[dataIndex],
                        colorText = Color.White
                    )
                }
            )
        }
    }
}
