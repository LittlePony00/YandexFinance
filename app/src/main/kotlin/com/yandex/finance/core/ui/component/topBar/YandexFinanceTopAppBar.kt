package com.yandex.finance.core.ui.component.topBar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.theme.RobotoTitleLargeStyle
import com.yandex.finance.core.ui.theme.YandexFinanceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YandexFinanceTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = RobotoTitleLargeStyle,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    topBarType: TopAppBarType = TopAppBarType.CENTER_ALIGNED,
) {
    CompositionLocalProvider(
        LocalTextStyle provides textStyle
    ) {
        when (topBarType) {
            TopAppBarType.DEFAULT -> {
                TopAppBar(
                    modifier = modifier,
                    title = title,
                    navigationIcon = navigationIcon,
                    actions = actions,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        actionIconContentColor = MaterialTheme.colorScheme.surfaceVariant,
                        navigationIconContentColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }

            TopAppBarType.CENTER_ALIGNED -> {
                CenterAlignedTopAppBar(
                    modifier = modifier,
                    title = title,
                    navigationIcon = navigationIcon,
                    actions = actions,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        navigationIconContentColor = MaterialTheme.colorScheme.surfaceVariant,
                        actionIconContentColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun YandexFinanceTopAppBarPreview() {
    YandexFinanceTheme {
        Column {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = "Title")
                },
                topBarType = TopAppBarType.DEFAULT
            )
            Spacer(modifier = Modifier.height(100.dp))
            YandexFinanceTopAppBar(
                title = {
                    Text(text = "Title")
                },
            )
        }
    }
}
