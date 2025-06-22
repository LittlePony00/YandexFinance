package com.yandex.finance.core.ui.util

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * This function is used to decrease code size for using Spacer with height modifier.
 *
 * @param [height] the height parameter for spacer.
 */
@Composable
@NonRestartableComposable
fun ColumnScope.SpacerHeight(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

/**
 * This function is used to decrease code size for using Spacer with width modifier.
 *
 * @param [width] the width parameter for spacer.
 */
@Composable
@NonRestartableComposable
fun RowScope.SpacerWidth(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

/**
 * This function is used to decrease code size for using Spacer with weight modifier.
 *
 * @param [weight] the weight parameter for spacer.
 */
@Composable
@NonRestartableComposable
fun RowScope.SpacerWithWeight(weight: Float) {
    Spacer(modifier = Modifier.weight(1f))
}

/**
 * This function is used to decrease code size for using Spacer with weight modifier.
 *
 * @param [weight] the weight parameter for spacer.
 */
@Composable
@NonRestartableComposable
fun ColumnScope.SpacerWithWeight(weight: Float) {
    Spacer(modifier = Modifier.weight(1f))
}
