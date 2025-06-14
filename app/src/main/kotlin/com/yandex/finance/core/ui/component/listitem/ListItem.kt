package com.yandex.finance.core.ui.component.listitem

import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.component.utils.SpacerWithWeight
import com.yandex.finance.core.ui.theme.RobotoBodyLargeStyle
import com.yandex.finance.core.ui.theme.RobotoBodyMediumStyle
import com.yandex.finance.core.ui.theme.YandexFinanceTheme
import io.github.composegears.valkyrie.TestIcon

@Composable
fun ListItem(
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    isOnClickEnabled: Boolean = true,
    indication: Indication? = ripple(),
    verticalSpaceBetweenItems: Dp = 0.dp,
    horizontalSpaceBetweenItems: Dp = 16.dp,
    textStyle: TextStyle = RobotoBodyLargeStyle,
    interactionSource: MutableInteractionSource? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    trailingContent: (@Composable RowScope.() -> Unit)? = null,
    navigationContent: (@Composable RowScope.() -> Unit)? = null,
    contentPaddings: PaddingValues = PaddingValues(vertical = 22.dp, horizontal = 16.dp),
) {
    CompositionLocalProvider(
        LocalTextStyle provides textStyle,
        LocalContentColor provides contentColor,
    ) {
        Box(
            modifier = modifier
                .background(containerColor)
                .clickable(
                    onClick = onClick,
                    indication = indication,
                    enabled = isOnClickEnabled,
                    interactionSource = interactionSource
                ),
        ) {
            Row(
                modifier = modifier.padding(contentPaddings),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(horizontalSpaceBetweenItems)
            ) {
                navigationContent?.invoke(this)
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = verticalSpaceBetweenItems,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    content()
                }
                SpacerWithWeight(weight = 1f)
                trailingContent?.invoke(this)
            }
        }
    }
}

@Preview
@Composable
private fun ListItemPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center,
        ) {
            ListItem(
                content = {
                    Text(text = "Аренда квартиры")
                    Text(
                        text = "Subtext",
                        style = RobotoBodyMediumStyle,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    )
                },
                onClick = {},
                navigationContent = {
                    Image(
                        imageVector = Icons.TestIcon,
                        contentDescription = Icons.TestIcon.name
                    )
                },
                trailingContent = {
                    Text(text = "100000 ₽")
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun ListPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.systemBars.asPaddingValues()),
            ) {
                items(100) {
                    ListItem(
                        content = {
                            Text(text = "Аренда квартиры")
                            Text(
                                text = "Subtext",
                                style = RobotoBodyMediumStyle,
                                color = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        },
                        onClick = {},
                        navigationContent = {
                            Image(
                                imageVector = Icons.TestIcon,
                                contentDescription = Icons.TestIcon.name
                            )
                        },
                        trailingContent = {
                            Text(text = "100000 ₽")
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                tint = MaterialTheme.colorScheme.tertiary,
                                contentDescription = null
                            )
                        }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                }
            }
        }
    }
}
