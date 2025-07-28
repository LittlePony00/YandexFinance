package com.yandex.finance.feature.account.impl.presentation.component

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
import androidx.compose.foundation.layout.width
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
import com.yandex.finance.feature.account.impl.presentation.viewmodel.BalanceChartItem
import kotlinx.coroutines.delay
import kotlin.math.abs

@Composable
fun BalanceChart(
    data: List<BalanceChartItem>,
    detailedInformationContent: @Composable BoxWithConstraintsScope.(offset: Offset, dataIndex: Int) -> Unit,
    modifier: Modifier = Modifier,
    barThickness: Float = 16f,
    textInfoSize: Dp = 12.dp,
    spaceFromLeft: Float = 20f,
    lineColor: Color = MaterialTheme.colorScheme.primary,
    barCornerRadius: Dp = 32.dp,
    strokeLineWidth: Float = 5f,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    shouldDrawLines: Boolean = false,
) {
    if (data.isEmpty()) return

    var dataIndex by remember { mutableIntStateOf(0) }
    var dataOffset by remember { mutableStateOf<Offset?>(null) }

    var isAnimationStarted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        isAnimationStarted = true
    }

    val maxAbsValue = data.maxOfOrNull { abs(it.balance) } ?: 1.0

    val animatedValues = data.mapIndexed { index, value ->
        val animatedValue by animateFloatAsState(
            targetValue = if (!isAnimationStarted) 0f else abs(value.balance).toFloat(),
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = index * 100
            ),
            label = ""
        )
        animatedValue
    }

    BoxWithConstraints(modifier = modifier) {
        val availableWidth = constraints.maxWidth.toFloat()
        val spacing = 16f
        val maxBars =
            ((availableWidth + spacing) / (barThickness + spacing)).toInt().coerceAtLeast(1)
        val visibleData = if (data.size > maxBars) data.take(maxBars) else data

        Canvas(modifier = Modifier.matchParentSize()) {
            val totalBarsWidth = visibleData.size * barThickness + (visibleData.size - 1) * spacing
            val startX = (size.width - totalBarsWidth) / 2

            if (shouldDrawLines) {
                for (i in 0..5) {
                    val y = size.height - (size.height * (i / 5f))

                    drawLine(
                        strokeWidth = 1.dp.toPx(),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        color = lineColor.copy(alpha = .5f),
                    )
                }
            }

            val visibleAnimatedValues = animatedValues.take(visibleData.size)
            visibleAnimatedValues.forEachIndexed { index, value ->
                val item = visibleData[index]
                val barHeight = (value / maxAbsValue.toFloat()) * size.height
                val x = startX + index * (barThickness + spacing)
                val rawRadius = barCornerRadius.toPx()
                val maxAllowedRadius = minOf(rawRadius, barThickness / 2, barHeight / 2) + 10

                val barColor = if (item.isIncrease) {
                    Color(0xFF4CAF50)
                } else {
                    Color(0xFFFF9800)
                }

                val barPath = Path().apply {
                    moveTo(x, size.height)
                    lineTo(x, size.height - barHeight + maxAllowedRadius)
                    arcTo(
                        Rect(
                            left = x,
                            top = size.height - barHeight,
                            right = x + maxAllowedRadius,
                            bottom = size.height - barHeight + maxAllowedRadius
                        ),
                        180f,
                        90f,
                        false
                    )
                    lineTo(x + barThickness - maxAllowedRadius, size.height - barHeight)
                    arcTo(
                        Rect(
                            left = x + barThickness - maxAllowedRadius,
                            top = size.height - barHeight,
                            right = x + barThickness,
                            bottom = size.height - barHeight + maxAllowedRadius
                        ),
                        270f,
                        90f,
                        false
                    )
                    lineTo(x + barThickness, size.height)
                    close()
                }

                drawPath(
                    path = barPath,
                    color = barColor
                )

                if (
                    visibleData.size > 10 &&
                    (index % (visibleData.size / 2) == 0 || index == visibleData.lastIndex || index == 0)
                ) {
                    drawContext.canvas.nativeCanvas.apply {
                        val cleanDate = item.date.split("_")[0]
                        val dateText = try {
                            if (cleanDate.contains("T")) {
                                val datePart = cleanDate.split("T")[0]
                                val parts = datePart.split("-")
                                if (parts.size >= 3) {
                                    "${parts[2]}.${parts[1]}"
                                } else {
                                    cleanDate.substring(5, 10)
                                }
                            } else {
                                cleanDate.substring(5, 10)
                            }
                        } catch (e: Exception) {
                            item.date
                        }
                        drawText(
                            dateText,
                            x + barThickness / 2,
                            size.height + 15.dp.toPx(),
                            Paint().apply {
                                color = textColor.toArgb()
                                textAlign = Paint.Align.CENTER
                                textSize = textInfoSize.toPx()
                            }
                        )
                    }
                }
            }

            if (shouldDrawLines) {
                drawLine(
                    color = lineColor,
                    strokeWidth = strokeLineWidth,
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = 0f, y = size.height),
                )

                drawLine(
                    color = lineColor,
                    strokeWidth = strokeLineWidth,
                    start = Offset(x = -2.5f, y = size.height),
                    end = Offset(x = size.width, y = size.height),
                )
            }
        }
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        dataIndex =
                            (offset.x / ((size.width - barThickness + spaceFromLeft) / data.size))
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
private fun BalanceChartPreview() {
    val testData = listOf(
        BalanceChartItem(
            balance = 60000.0,
            change = 60000.0,
            date = "2024-07-26",
            isIncrease = true
        ),
        BalanceChartItem(
            balance = -1000.0,
            change = -1000.0,
            date = "2024-07-25",
            isIncrease = false
        ),
        BalanceChartItem(
            balance = -1000.0,
            change = -1000.0,
            date = "2024-07-24",
            isIncrease = false
        ),
        BalanceChartItem(
            balance = 26000.0,
            change = 26000.0,
            date = "2024-07-23",
            isIncrease = true
        ),
        BalanceChartItem(
            balance = 26000.0,
            change = 26000.0,
            date = "2024-07-22",
            isIncrease = true
        ),
        BalanceChartItem(
            balance = -5000.0,
            change = -5000.0,
            date = "2024-07-21",
            isIncrease = false
        ),
        BalanceChartItem(
            balance = 15000.0,
            change = 15000.0,
            date = "2024-07-20",
            isIncrease = true
        ),
        BalanceChartItem(
            balance = -2000.0,
            change = -2000.0,
            date = "2024-07-19",
            isIncrease = false
        ),
        BalanceChartItem(balance = 8000.0, change = 8000.0, date = "2024-07-18", isIncrease = true),
        BalanceChartItem(
            balance = -3000.0,
            change = -3000.0,
            date = "2024-07-17",
            isIncrease = false
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        BalanceChart(
            modifier = Modifier
                .height(300.dp)
                .width(300.dp),
            data = testData,
            detailedInformationContent = { _, _ -> }
        )
    }
} 