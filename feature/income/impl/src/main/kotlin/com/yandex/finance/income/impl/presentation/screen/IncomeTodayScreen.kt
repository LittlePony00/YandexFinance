package com.yandex.finance.income.impl.presentation.screen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yandex.finance.core.ui.component.button.FabButton
import com.yandex.finance.core.ui.component.button.PrimaryButton
import com.yandex.finance.core.ui.component.icon.EmojiWrapper
import com.yandex.finance.core.ui.component.icon.History
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.core.ui.theme.RobotoBodyLargeStyle
import com.yandex.finance.core.ui.theme.RobotoLabelMediumStyle
import com.yandex.finance.core.ui.util.formatWithSeparator
import com.yandex.finance.feature.income.impl.R
import com.yandex.finance.feature.outcome.api.domain.model.UiTransactionMainModel
import com.yandex.finance.feature.outcome.api.domain.model.UiTransactionModel
import com.yandex.finance.income.impl.presentation.viewmodel.IncomeTodayViewModel

@Composable
fun IncomeTodayScreen(
    onHistoryClick: () -> Unit,
    onFabButtonClick: () -> Unit,
    onIncomeClick: (UiTransactionModel) -> Unit,
    incomeTodayVM: IncomeTodayViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState = incomeTodayVM.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        incomeTodayVM.loadData()
    }

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
            FabButton(onClick = onFabButtonClick)
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
                    incomeTodayUiState = incomeTodayUiState,
                    onIncomeClick = onIncomeClick
                )
            }
        }
    }
}

@Composable
private fun IncomeTodayScreenContent(
    onIncomeClick: (UiTransactionModel) -> Unit,
    modifier: Modifier = Modifier,
    incomeTodayUiState: State<UiTransactionMainModel>
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            ListItem(
                contentPaddings = PaddingValues(
                    vertical = 16.dp,
                    horizontal = 16.dp
                ),
                containerColor = MaterialTheme.colorScheme.secondary,
                content = {
                    Text(text = stringResource(R.string.total))
                },
                trailingContent = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = incomeTodayUiState
                                .value
                                .sumOfAllTransactions
                                .toInt()
                                .toString()
                                .formatWithSeparator()
                        )
                        Text(
                            text = " ${
                                incomeTodayUiState
                                    .value
                                    .currentCurrencyType
                                    .type
                            }"
                        )
                    }
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }

        items(incomeTodayUiState.value.transactions) { transaction ->
            ListItem(
                content = {
                    Text(text = transaction.category.name)
                    transaction.comment?.let { comment ->
                        if (comment.isNotBlank()) {
                            Text(
                                text = comment,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = RobotoLabelMediumStyle
                            )
                        }
                    }
                },
                onClick = {
                    onIncomeClick(transaction)
                },
                navigationContent = {
                    EmojiWrapper(
                        text = transaction.category.name,
                        emoji = transaction.category.emoji,
                    )
                },
                trailingContent = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "${transaction.amount.toDouble().toInt()} ")
                        Text(text = transaction.account.currency.type)
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
