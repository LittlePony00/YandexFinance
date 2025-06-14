package com.yandex.finance.feature.account.presentation.screen

import androidx.compose.foundation.Image
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
import com.yandex.finance.R
import com.yandex.finance.core.ui.component.button.FabButton
import com.yandex.finance.core.ui.component.icon.Edit
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.feature.account.domain.UiAccountModel
import com.yandex.finance.feature.account.presentation.viewmodel.MyAccountViewModel
import io.github.composegears.valkyrie.TestIcon

@Composable
fun MyAccountScreen(
    modifier: Modifier = Modifier,
    myAccountVM: MyAccountViewModel,
) {
    val uiState = myAccountVM.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.my_account))
                },
                actions = {
                    IconButton(
                        onClick = {}
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
                    Text(text = "Error") // Пока так
                }
            }

            is MyAccountViewModel.State.Content -> {
                val accountUiState = state.accountUiState.collectAsStateWithLifecycle()

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
                Text(text = "Баланс")
            },
            navigationContent = {
                Image(
                    imageVector = Icons.TestIcon,
                    contentDescription = Icons.TestIcon.name
                )
            },
            trailingContent = {
                Text(text = "600 000 ₽")
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
                Text(text = "Валюта")
            },
            trailingContent = {
                Text(text = "₽")
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = Icons.AutoMirrored.Filled.KeyboardArrowRight.name,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        )
    }
}
