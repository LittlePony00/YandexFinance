package com.yandex.finance.core.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.theme.RobotoLabelLargeStyle
import com.yandex.finance.core.ui.theme.YandexFinanceTheme

@Composable
fun PrimaryButton(
    text: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    leftIcon: ImageVector? = null,
    rightIcon: ImageVector? = null,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ),
    progressIndicatorColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    Button(
        modifier = modifier.height(ButtonDefaults.MinHeight),
        enabled = isEnabled,
        onClick = onButtonClick,
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 12.dp
        ),
        colors = buttonColors,
        shape = RoundedCornerShape(size = 16.dp),
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.aspectRatio(1f),
                    color = progressIndicatorColor,
                    strokeWidth = 2.dp,
                )
            }
            Row(
                modifier = Modifier.graphicsLayer {
                    alpha = if (isLoading) 0f else 1f
                },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                leftIcon?.let { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                    )
                }
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = text,
                    style = RobotoLabelLargeStyle
                )
                rightIcon?.let { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Test",
                    onButtonClick = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Test",
                    isLoading = true,
                    onButtonClick = {}
                )
            }
        }
    }
}
