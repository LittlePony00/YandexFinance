package com.yandex.finance.feature.transaction_analysis.impl.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yandex.finance.core.ui.component.button.PrimaryButton
import com.yandex.finance.core.ui.component.calendar.YandexFinanceCalendar
import com.yandex.finance.core.ui.component.icon.EmojiWrapper
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.core.ui.theme.RobotoBodyLargeStyle
import com.yandex.finance.core.ui.theme.RobotoLabelMediumStyle
import com.yandex.finance.core.ui.util.formatWithSeparator
import com.yandex.finance.feature.transaction_analysis.api.domain.model.CategoryAnalysisItem
import com.yandex.finance.feature.transaction_analysis.api.domain.model.TransactionAnalysisModel
import com.yandex.finance.feature.transaction_analysis.impl.R
import com.yandex.finance.feature.transaction_analysis.impl.presentation.viewmodel.TransactionAnalysisViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionAnalysisScreen(
    onBackClick: () -> Unit,
    onItemClick: (CategoryAnalysisItem) -> Unit,
    transactionAnalysisVM: TransactionAnalysisViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by transactionAnalysisVM.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        transactionAnalysisVM.loadData()
    }

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.analysis_title))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        when (val state = uiState) {
            is TransactionAnalysisViewModel.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is TransactionAnalysisViewModel.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.error_message),
                            style = RobotoBodyLargeStyle
                        )
                        PrimaryButton(
                            text = stringResource(R.string.retry_button),
                            onButtonClick = state.retry
                        )
                    }
                }
            }

            is TransactionAnalysisViewModel.State.Content -> {
                val analysisState by state.analysisUiState.collectAsStateWithLifecycle()
                
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                ) {
                    item {
                        PeriodSelectionSection(
                            analysisModel = analysisState,
                            onStartDateClick = { transactionAnalysisVM.changeAction(TransactionAnalysisViewModel.Action.ChangeStartDate) },
                            onEndDateClick = { transactionAnalysisVM.changeAction(TransactionAnalysisViewModel.Action.ChangeEndDate) }
                        )
                    }

                    item {
                        TotalAmountSection(analysisModel = analysisState)
                    }

                    item {
                        CategoryChartSection(analysisModel = analysisState)
                    }

                    items(
                        items = analysisState.categoryAnalysis,
                        key = { it.categoryId }
                    ) { categoryItem ->
                        CategoryAnalysisItem(
                            categoryItem = categoryItem,
                            onItemClick = onItemClick
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                }

                when (val action = state.action) {
                    is TransactionAnalysisViewModel.Action.ChangeEndDate, 
                    TransactionAnalysisViewModel.Action.ChangeStartDate -> {
                        YandexFinanceCalendar(
                            onDismiss = {
                                transactionAnalysisVM.changeAction(null)
                            },
                            onDateSelected = {
                                if (action is TransactionAnalysisViewModel.Action.ChangeStartDate) {
                                    transactionAnalysisVM.changeStartDate(it)
                                } else {
                                    transactionAnalysisVM.changeEndDate(it)
                                }
                            }
                        )
                    }
                    else -> { /* Do nothing */ }
                }
            }
        }
    }
}

@Composable
private fun PeriodSelectionSection(
    analysisModel: TransactionAnalysisModel,
    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ListItem(
            modifier = Modifier.height(64.dp),
            contentPaddings = PaddingValues(16.dp),
            navigationContent = {
                Text(text = stringResource(R.string.period_start))
            },
            trailingContent = {
                AssistChip(
                    border = null,
                    shape = RoundedCornerShape(16.dp),
                    onClick = onStartDateClick,
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    label = {
                        Text(
                            text = analysisModel.startDate.ifEmpty { stringResource(R.string.select_date) },
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        ListItem(
            modifier = Modifier.height(64.dp),
            contentPaddings = PaddingValues(16.dp),
            navigationContent = {
                Text(text = stringResource(R.string.period_end))
            },
            trailingContent = {
                AssistChip(
                    border = null,
                    shape = RoundedCornerShape(16.dp),
                    onClick = onEndDateClick,
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    label = {
                        Text(
                            text = analysisModel.endDate.ifEmpty { stringResource(R.string.select_date) },
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Composable
private fun TotalAmountSection(
    analysisModel: TransactionAnalysisModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ListItem(
            navigationContent = {
                Text(text = stringResource(R.string.total_amount))
            },
            trailingContent = {
                Text(
                    text = "${analysisModel.totalAmount.toInt().toString().formatWithSeparator()} ${analysisModel.currency.type}",
                    style = RobotoBodyLargeStyle
                )
            }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Composable
private fun CategoryChartSection(
    analysisModel: TransactionAnalysisModel,
    modifier: Modifier = Modifier
) {
    // TODO: Реализовать круговую диаграмму
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.chart_placeholder),
                style = RobotoBodyLargeStyle
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun CategoryAnalysisItem(
    categoryItem: CategoryAnalysisItem,
    onItemClick: (CategoryAnalysisItem) -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        onClick = { onItemClick(categoryItem) },
        content = {
            Text(
                text = categoryItem.categoryName,
                style = RobotoBodyLargeStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            categoryItem.description?.let { description ->
                Text(
                    text = description,
                    style = RobotoLabelMediumStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationContent = {
            EmojiWrapper(
                text = categoryItem.categoryName,
                emoji = categoryItem.categoryEmoji
            )
        },
        trailingContent = {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${String.format("%.0f", categoryItem.percentage)}%",
                    style = RobotoBodyLargeStyle
                )
                Text(
                    text = "${categoryItem.amount.toInt().toString().formatWithSeparator()} ₽",
                    style = RobotoLabelMediumStyle
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        }
    )
} 