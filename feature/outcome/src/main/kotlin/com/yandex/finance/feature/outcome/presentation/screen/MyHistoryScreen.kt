package com.yandex.finance.feature.outcome.presentation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.component.calendar.YandexFinanceCalendar
import com.yandex.finance.core.ui.component.icon.Analys
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.core.ui.theme.RobotoLabelMediumStyle
import com.yandex.finance.feature.outcome.R

@Composable
fun MyHistoryScreen(
    onBackClick: () -> Unit,
    onAnalysClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.my_history))
                },
                actions = {
                    IconButton(
                        onClick = onAnalysClick
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Analys,
                            contentDescription = Icons.Analys.name
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = Icons.AutoMirrored.Filled.ArrowBack.name
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        MyHistoryScreenContent(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
private fun MyHistoryScreenContent(
    modifier: Modifier = Modifier
) {
    var state by remember { mutableStateOf(false) }

    if (state) {
        YandexFinanceCalendar(
            onDateSelected = { state = false },
            onDismiss = { state = false },
        )
    }

    LazyColumn(modifier = modifier) {

        item {
            ListItem(
                onClick = { state = true },
                content = {},
                containerColor = MaterialTheme.colorScheme.secondary,
                navigationContent = {
                    Text(text = "Начало")
                },
                trailingContent = {
                    Text(text = "Февраль 2025")
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }

        item {
            ListItem(
                onClick = {},
                content = {},
                containerColor = MaterialTheme.colorScheme.secondary,
                navigationContent = {
                    Text(text = "Конец")
                },
                trailingContent = {
                    Text(text = "Февраль 2025")
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }

        item {
            ListItem(
                onClick = {},
                content = {},
                containerColor = MaterialTheme.colorScheme.secondary,
                navigationContent = {
                    Text(text = "Сумма")
                },
                trailingContent = {
                    Text(text = "Февраль 2025")
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }

        items(5) {
            ListItem(
                onClick = {},
                isOnClickEnabled = false,
                content = {
                    Text(text = "На собачку")
                    Text(
                        text = "На собачку",
                        style = RobotoLabelMediumStyle
                    )
                },
                navigationContent = {
                    Text(text = "Начало")
                },
                trailingContent = {
                    Text(text = "Февраль 2025")
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
    }
}
