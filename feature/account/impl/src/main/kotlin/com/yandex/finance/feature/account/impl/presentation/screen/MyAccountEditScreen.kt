package com.yandex.finance.feature.account.impl.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yandex.finance.core.domain.model.CurrencyType
import com.yandex.finance.core.ui.component.button.PrimaryButton
import com.yandex.finance.core.ui.component.icon.Confirm
import com.yandex.finance.core.ui.component.icon.EmojiWrapper
import com.yandex.finance.core.ui.component.indicator.PopupWithLoadingIndicator
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.core.ui.util.SpacerHeight
import com.yandex.finance.core.ui.util.formatWithSeparator
import com.yandex.finance.feature.account.impl.R
import com.yandex.finance.core.ui.component.bottomsheet.ChangeNameBottomSheet
import com.yandex.finance.core.ui.component.bottomsheet.ChangeBalanceBottomSheet
import com.yandex.finance.feature.account.impl.presentation.component.ChooseCurrencyBottomSheet
import com.yandex.finance.feature.account.impl.presentation.viewmodel.MyAccountEditViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun MyAccountEditScreen(
    onCancelClick: () -> Unit,
    myAccountEditVM: MyAccountEditViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onCancelClick()
    }

    val uiState = myAccountEditVM.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(myAccountEditVM.sideEffect) {
        myAccountEditVM.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is MyAccountEditViewModel.SideEffect.NavigateBack -> {
                    onCancelClick()
                }

                is MyAccountEditViewModel.SideEffect.AccountSaved -> {

                }

                is MyAccountEditViewModel.SideEffect.AccountDeleted -> {
                    onCancelClick()
                }

                is MyAccountEditViewModel.SideEffect.ShowNetworkError -> {
                    snackbarHostState.showSnackbar(context.getString(R.string.error_network))
                }

                is MyAccountEditViewModel.SideEffect.ShowSaveError -> {
                    snackbarHostState.showSnackbar(context.getString(R.string.error_save_account))
                }

                is MyAccountEditViewModel.SideEffect.ShowDeleteError -> {
                    snackbarHostState.showSnackbar(context.getString(R.string.error_delete_account))
                }
            }
        }
    }

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(
                        text = (uiState.value as? MyAccountEditViewModel.State.Content)?.tempAccountName
                            ?: ""
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            myAccountEditVM.handleIntent(MyAccountEditViewModel.Intent.SaveChanges)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Confirm,
                            contentDescription = Icons.Confirm.name
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            myAccountEditVM.handleIntent(MyAccountEditViewModel.Intent.HideBottomSheet)
                            onCancelClick()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = Icons.Default.Close.name
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->

        when (val state = uiState.value) {
            is MyAccountEditViewModel.State.Error -> {
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(context.getString(R.string.error_text))
                }
            }

            is MyAccountEditViewModel.State.Content -> {
                MyAccountEditScreenContent(
                    modifier = modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    onNameClick = {
                        myAccountEditVM.handleIntent(MyAccountEditViewModel.Intent.ShowAccountNameBottomSheet)
                    },
                    onBalanceClick = {
                        myAccountEditVM.handleIntent(MyAccountEditViewModel.Intent.ShowBalanceBottomSheet)
                    },
                    onCurrencyClick = {
                        myAccountEditVM.handleIntent(MyAccountEditViewModel.Intent.ShowCurrencyBottomSheet)
                    },
                    onDeleteAccount = {
                        myAccountEditVM.handleIntent(MyAccountEditViewModel.Intent.DeleteAccount)
                    },
                    state = state
                )

                when (state.action) {
                    is MyAccountEditViewModel.Action.Loading -> {
                        PopupWithLoadingIndicator()
                    }

                    is MyAccountEditViewModel.Action.ShowAccountNameDialog -> {
                        ChangeNameBottomSheet(
                            initialValue = state.tempAccountName,
                            onDismiss = {
                                myAccountEditVM.handleIntent(MyAccountEditViewModel.Intent.HideBottomSheet)
                            },
                            onSave = { newName ->
                                myAccountEditVM.handleIntent(MyAccountEditViewModel.Intent.SaveAccountNameChanges(newName))
                            }
                        )
                    }

                    is MyAccountEditViewModel.Action.ShowBalanceDialog -> {
                        ChangeBalanceBottomSheet(
                            initialValue = state.tempBalance,
                            onDismiss = {
                                myAccountEditVM.handleIntent(MyAccountEditViewModel.Intent.HideBottomSheet)
                            },
                            onSave = { newBalance ->
                                myAccountEditVM.handleIntent(MyAccountEditViewModel.Intent.SaveBalanceChanges(newBalance))
                            }
                        )
                    }

                    is MyAccountEditViewModel.Action.ShowCurrencyDialog -> {
                        ChooseCurrencyBottomSheet(
                            onDismiss = {
                                myAccountEditVM.handleIntent(MyAccountEditViewModel.Intent.HideBottomSheet)
                            },
                            onEuroClick = {
                                myAccountEditVM.handleIntent(
                                    MyAccountEditViewModel.Intent.SelectCurrency(
                                        CurrencyType.EUR
                                    )
                                )
                            },
                            onDollarClick = {
                                myAccountEditVM.handleIntent(
                                    MyAccountEditViewModel.Intent.SelectCurrency(
                                        CurrencyType.USD
                                    )
                                )
                            },
                            onRussianRubleClick = {
                                myAccountEditVM.handleIntent(
                                    MyAccountEditViewModel.Intent.SelectCurrency(
                                        CurrencyType.RUB
                                    )
                                )
                            }
                        )
                    }

                    else -> {
                        /* Do nothing */
                    }
                }
            }
        }
    }
}

@Composable
private fun MyAccountEditScreenContent(
    onNameClick: () -> Unit,
    onBalanceClick: () -> Unit,
    onCurrencyClick: () -> Unit,
    onDeleteAccount: () -> Unit,
    state: MyAccountEditViewModel.State.Content,
    modifier: Modifier = Modifier
) {
    val listItemModifier = Modifier.fillMaxWidth()

    Column(
        modifier = modifier
    ) {
        ListItem(
            modifier = listItemModifier,
            onClick = onNameClick,
            content = {
                Text(
                    text = stringResource(R.string.account_name),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationContent = {
                EmojiWrapper(
                    text = "",
                    emoji = state.accountData.icon
                )
            },
            trailingContent = {
                Text(text = state.tempAccountName)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = Icons.AutoMirrored.Filled.KeyboardArrowRight.name,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        ListItem(
            modifier = listItemModifier,
            onClick = onBalanceClick,
            navigationContent = {
                Text(
                    text = stringResource(R.string.balance),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            trailingContent = {
                Text(text = state.tempBalance.formatWithSeparator())
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = Icons.AutoMirrored.Filled.KeyboardArrowRight.name,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        ListItem(
            modifier = listItemModifier,
            onClick = onCurrencyClick,
            navigationContent = {
                Text(text = stringResource(R.string.currency))
            },
            trailingContent = {
                Text(text = state.tempCurrency.type)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = Icons.AutoMirrored.Filled.KeyboardArrowRight.name,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        SpacerHeight(height = 16.dp)
        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.delete_account),
            onButtonClick = onDeleteAccount,
            buttonColors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        )
    }
}
