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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.yandex.finance.core.ui.component.button.PrimaryButton
import com.yandex.finance.core.ui.component.calendar.YandexFinanceCalendar
import com.yandex.finance.core.ui.component.icon.Analys
import com.yandex.finance.core.ui.component.icon.EmojiWrapper
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.core.ui.theme.RobotoBodyLargeStyle
import com.yandex.finance.core.ui.theme.RobotoLabelMediumStyle
import com.yandex.finance.core.ui.util.formatWithSeparator
import com.yandex.finance.core.ui.util.localDateTimeFormatter
import com.yandex.finance.feature.income.impl.R
import com.yandex.finance.feature.outcome.api.domain.model.UiMyHistoryModel
import com.yandex.finance.income.impl.presentation.viewmodel.MyHistoryIncomeViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime

// Возможно можно вынести в отдельный модуль вместе с viewmodel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyHistoryScreen(
    onBackClick: () -> Unit,
    onAnalysClick: () -> Unit,
    myHistoryVM: MyHistoryIncomeViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState = myHistoryVM.uiState.collectAsStateWithLifecycle()

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
        when (val state = uiState.value) {
            is MyHistoryIncomeViewModel.State.Error -> {
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

            is MyHistoryIncomeViewModel.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onSurface)
                }
            }

            is MyHistoryIncomeViewModel.State.Content -> {
                val myHistoryUiState = state.myHistoryUiState.collectAsStateWithLifecycle()

                MyHistoryScreenContent(
                    modifier = modifier.padding(innerPadding),
                    myHistoryUiState = myHistoryUiState,
                    onBeginClick = {
                        myHistoryVM.changeAction(action = MyHistoryIncomeViewModel.Action.ChangeStartDate)
                    },
                    onEndClick = {
                        myHistoryVM.changeAction(action = MyHistoryIncomeViewModel.Action.ChangeEndDate)
                    }
                )

                when (val action = state.action) {
                    is MyHistoryIncomeViewModel.Action.ChangeEndDate, MyHistoryIncomeViewModel.Action.ChangeStartDate -> {
                        YandexFinanceCalendar(
                            onDismiss = {
                                myHistoryVM.changeAction(null)
                            },
                            onDateSelected = {
                                if (action is MyHistoryIncomeViewModel.Action.ChangeStartDate) {
                                    myHistoryVM.changeStartDate(startDate = it)
                                } else {
                                    myHistoryVM.changeEndDate(endDate = it)
                                }
                            }
                        )
                    }

                    else -> { /* Do nothing */
                    }
                }
            }
        }
    }
}

@Composable
private fun MyHistoryScreenContent(
    onEndClick: () -> Unit,
    onBeginClick: () -> Unit,
    myHistoryUiState: State<UiMyHistoryModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            ListItem(
                onClick = onBeginClick,
                content = {},
                contentPaddings = PaddingValues(
                    vertical = 16.dp,
                    horizontal = 16.dp
                ),
                containerColor = MaterialTheme.colorScheme.secondary,
                navigationContent = {
                    Text(text = stringResource(R.string.beginning))
                },
                trailingContent = {
                    Text(text = myHistoryUiState.value.startDate)
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }

        item {
            ListItem(
                onClick = onEndClick,
                content = {},
                contentPaddings = PaddingValues(
                    vertical = 16.dp,
                    horizontal = 16.dp
                ),
                containerColor = MaterialTheme.colorScheme.secondary,
                navigationContent = {
                    Text(text = stringResource(R.string.end))
                },
                trailingContent = {
                    Text(text = myHistoryUiState.value.endDate)
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }

        item {
            ListItem(
                onClick = {},
                content = {},
                contentPaddings = PaddingValues(
                    vertical = 16.dp,
                    horizontal = 16.dp
                ),
                containerColor = MaterialTheme.colorScheme.secondary,
                navigationContent = {
                    Text(text = stringResource(R.string.sum))
                },
                trailingContent = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${myHistoryUiState
                                .value
                                .uiTransactionMainModel
                                .sumOfAllTransactions
                                .toInt()
                                .toString()
                                .formatWithSeparator()} "
                        )
                        Text(
                            text =
                                myHistoryUiState.value.uiTransactionMainModel.currentCurrencyType.type
                        )
                    }
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }

        items(myHistoryUiState.value.uiTransactionMainModel.transactions) { transaction ->
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
                navigationContent = {
                    EmojiWrapper(
                        text = transaction.category.name,
                        emoji = transaction.category.emoji,
                    )
                },
                trailingContent = {
                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "${transaction.amount.toDouble().toInt().toString().formatWithSeparator()} ")
                            Text(text = transaction.account.currency.type)
                        }
                        Text(
                            text = Instant.parse(transaction.updatedAt)
                                .toLocalDateTime(TimeZone.UTC)
                                .format(localDateTimeFormatter)
                        )
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
