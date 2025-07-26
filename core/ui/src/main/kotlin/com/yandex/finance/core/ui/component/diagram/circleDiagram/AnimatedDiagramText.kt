package com.yandex.finance.core.ui.component.diagram.circleDiagram

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle

@Composable
internal fun AnimatedDiagramText(
    text: AnnotatedString,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier
) {
    var displayedText by remember { mutableStateOf(text) }

    SideEffect {
        displayedText = text
    }

    AnimatedContent(
        targetState = displayedText,
        transitionSpec = {
            (fadeIn(
                animationSpec = tween(durationMillis = 720, delayMillis = 90)
            ) + scaleIn(
                initialScale = 0.92f,
                animationSpec = tween(durationMillis = 220, delayMillis = 90)
            )).togetherWith(fadeOut(animationSpec = tween(90)))
        },
        label = "",
    ) { item ->
        Text(
            modifier = modifier,
            text = item,
            color = color,
            style = style,
        )
    }
}
