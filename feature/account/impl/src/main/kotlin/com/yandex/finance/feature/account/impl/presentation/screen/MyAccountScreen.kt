package com.yandex.finance.feature.account.impl.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yandex.finance.core.ui.component.button.FabButton
import com.yandex.finance.core.ui.component.button.PrimaryButton
import com.yandex.finance.core.ui.component.icon.Edit
import com.yandex.finance.core.ui.component.icon.EmojiWrapper
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.core.ui.theme.RobotoBodyLargeStyle
import com.yandex.finance.core.ui.util.formatWithSeparator
import com.yandex.finance.feature.account.impl.R
import com.yandex.finance.feature.account.impl.domain.UiAccountModel
import com.yandex.finance.feature.account.impl.presentation.viewmodel.MyAccountViewModel

@Composable
fun MyAccountScreen(
    onEditClick: (UiAccountModel) -> Unit,
    myAccountVM: MyAccountViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState = myAccountVM.uiState.collectAsStateWithLifecycle()
    val accountUiState = myAccountVM.accountUiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = accountUiState.value.name)
                },
                actions = {
                    IconButton(
                        onClick = { onEditClick(accountUiState.value) }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Edit,
                            contentDescription = Icons.Edit.name
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
            is MyAccountViewModel.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onSurface)
                }
            }

            is MyAccountViewModel.State.Error -> {
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

            is MyAccountViewModel.State.Content -> {
                MyAccountScreenContent(
                    modifier = modifier.padding(innerPadding),
                    accountUiState = accountUiState
                )
            }
        }
    }
}

@Composable
private fun MyAccountScreenContent(
    accountUiState: State<UiAccountModel>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        ListItem(
            onClick = {},
            contentPaddings = PaddingValues(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
            containerColor = MaterialTheme.colorScheme.secondary,
            content = {
                Text(text = stringResource(R.string.balance))
            },
            navigationContent = {
                EmojiWrapper(
                    text = "",
                    containerColor = MaterialTheme.colorScheme.surface,
                    emoji = accountUiState.value.icon
                )
            },
            trailingContent = {
                Text(
                    text = "${
                        accountUiState.value.balance.formatWithSeparator()
                    } ${
                        accountUiState.value.currency.type
                    }"
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = Icons.AutoMirrored.Filled.KeyboardArrowRight.name,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        ListItem(
            onClick = {},
            contentPaddings = PaddingValues(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
            containerColor = MaterialTheme.colorScheme.secondary,
            content = {
                Text(text = stringResource(R.string.currency))
            },
            trailingContent = {
                Text(text = accountUiState.value.currency.type)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = Icons.AutoMirrored.Filled.KeyboardArrowRight.name,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        )
    }
}
