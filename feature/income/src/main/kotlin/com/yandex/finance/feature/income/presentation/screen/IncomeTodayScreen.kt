package com.yandex.finance.feature.income.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.yandex.finance.core.ui.component.button.FabButton
import com.yandex.finance.core.ui.component.icon.History
import com.yandex.finance.core.ui.component.icon.TestIcon
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.core.ui.theme.RobotoBodyMediumStyle
import com.yandex.finance.feature.income.R
import com.yandex.finance.feature.income.domain.UiIncomeTodayModel
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
                    Text(text = "Error") // Пока так
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
    incomeTodayUiState: State<UiIncomeTodayModel>
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
                    Text(text = "Всего")
                },
                trailingContent = {
                    Text(text = incomeTodayUiState.value.sumOfAllIncomes.toString())
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }

        items(incomeTodayUiState.value.incomes) { income ->
            ListItem(
                content = {
                    Text(text = income.category.name)
                    income.comment?.let {
                        Text(
                            text = it,
                            style = RobotoBodyMediumStyle
                        )
                    }
                },
                onClick = {},
                navigationContent = {
                    Image(
                        imageVector = Icons.TestIcon,
                        contentDescription = Icons.TestIcon.name
                    )
                },
                trailingContent = {
                    Text(
                        modifier = Modifier,
                        text = "${income.account.balance} ${income.account.currency}",
                        softWrap = false,
                        overflow = TextOverflow.Clip
                    )
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
