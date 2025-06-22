package com.yandex.finance.feature.income.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yandex.finance.core.domain.model.CurrencyType
import com.yandex.finance.core.ui.component.button.FabButton
import com.yandex.finance.core.ui.component.button.PrimaryButton
import com.yandex.finance.core.ui.component.icon.EmojiWrapper
import com.yandex.finance.core.ui.component.icon.History
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.core.ui.theme.RobotoBodyLargeStyle
import com.yandex.finance.core.ui.theme.RobotoLabelMediumStyle
import com.yandex.finance.core.ui.util.formatWithSeparator
import com.yandex.finance.feature.income.R
import com.yandex.finance.feature.income.domain.UiIncomeMainModel
import com.yandex.finance.feature.income.presentation.viewmodel.IncomeTodayViewModel

@Composable
fun IncomeTodayScreen(
    onHistoryClick: () -> Unit,
    incomeTodayVM: IncomeTodayViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState = incomeTodayVM.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.income_today))
                },
                actions = {
                    IconButton(
                        onClick = onHistoryClick
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.History,
                            contentDescription = Icons.History.name
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FabButton(onClick = {})
        }
    ) { innerPadding ->
        when (val state = uiState.value) {
            is IncomeTodayViewModel.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onSurface)
                }
            }

            is IncomeTodayViewModel.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(R.string.error_text),
                            style = RobotoBodyLargeStyle
                        )
                        PrimaryButton(
                            text = stringResource(R.string.retry_text),
                            onButtonClick = {
                                state.retry.invoke()
                            }
                        )
                    }
                }
            }

            is IncomeTodayViewModel.State.Content -> {
                val incomeTodayUiState = state.incomeTodayUiState.collectAsStateWithLifecycle()

                IncomeTodayScreenContent(
                    modifier = modifier.padding(innerPadding),
                    incomeTodayUiState = incomeTodayUiState
                )
            }
        }
    }
}

@Composable
private fun IncomeTodayScreenContent(
    modifier: Modifier = Modifier,
    incomeTodayUiState: State<UiIncomeMainModel>
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            ListItem(
                onClick = {},
                contentPaddings = PaddingValues(
                    vertical = 16.dp,
                    horizontal = 16.dp
                ),
                isOnClickEnabled = false,
                containerColor = MaterialTheme.colorScheme.secondary,
                content = {
                    Text(text = stringResource(R.string.total))
                },
                trailingContent = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = incomeTodayUiState
                                .value
                                .sumOfAllIncomes
                                .toInt()
                                .toString()
                                .formatWithSeparator()
                        )
                        Text(
                            text = " ${
                                incomeTodayUiState
                                    .value
                                    .incomes
                                    .firstOrNull()
                                    ?.account
                                    ?.currency
                                    ?.type ?: CurrencyType.RUB.type
                            }"
                        )
                    }
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }

        items(incomeTodayUiState.value.incomes) { income ->
            ListItem(
                onClick = {},
                isOnClickEnabled = false,
                content = {
                    Text(text = income.category.name)
                    if (!income.comment.isNullOrEmpty()) {
                        Text(
                            text = income.comment,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = RobotoLabelMediumStyle
                        )
                    }
                },
                navigationContent = {
                    EmojiWrapper(
                        text = income.category.name,
                        emoji = income.category.emoji,
                    )
                },
                trailingContent = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "${income.amount.toDouble().toInt()} ")
                        Text(text = income.account.currency.type)
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = Icons.AutoMirrored.Filled.KeyboardArrowRight.name
                    )
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
    }
}
