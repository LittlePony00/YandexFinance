package com.yandex.finance.feature.transaction_edit.impl.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yandex.finance.core.ui.component.bottomsheet.ChangeBalanceBottomSheet
import com.yandex.finance.core.ui.component.bottomsheet.ChangeNameBottomSheet
import com.yandex.finance.core.ui.component.button.PrimaryButton
import com.yandex.finance.core.ui.component.calendar.YandexFinanceCalendar
import com.yandex.finance.core.ui.component.icon.Confirm
import com.yandex.finance.core.ui.component.timepicker.YFTimePicker
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.core.ui.provider.LocalSnackBarHostState
import com.yandex.finance.feature.transaction_edit.impl.R
import com.yandex.finance.feature.transaction_edit.impl.presentation.component.CategoryBottomSheet
import com.yandex.finance.feature.transaction_edit.impl.presentation.component.ListItemForTransactionEdit
import com.yandex.finance.feature.transaction_edit.impl.presentation.viewmodel.TransactionEditViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionEditScreen(
    isEdit: Boolean,
    isIncome: Boolean,
    modifier: Modifier = Modifier,
    viewModel: TransactionEditViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = LocalSnackBarHostState.current

    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is TransactionEditViewModel.SideEffect.NavigateBack -> {
                    Timber.d("NavigateBack side effect received, calling onNavigateBack()")
                    onNavigateBack()
                }

                is TransactionEditViewModel.SideEffect.TransactionSaved -> {
                    snackbarHostState.showSnackbar("Транзакция сохранена")
                }

                is TransactionEditViewModel.SideEffect.TransactionDeleted -> {
                    snackbarHostState.showSnackbar("Транзакция удалена")
                }

                is TransactionEditViewModel.SideEffect.ShowError -> {
                    snackbarHostState.showSnackbar("Произошла ошибка")
                }

                is TransactionEditViewModel.SideEffect.ShowToast -> {
                    snackbarHostState.showSnackbar(sideEffect.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(
                        text = if (isIncome) stringResource(R.string.my_income)
                        else stringResource(R.string.my_outcome)
                    )
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.handleIntent(TransactionEditViewModel.Intent.SaveTransaction) }
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
                        onClick = onNavigateBack
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
    ) { innerPadding ->
        when (val state = uiState) {
            is TransactionEditViewModel.State.Loading -> {
                LoadingContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is TransactionEditViewModel.State.Error -> {
                ErrorContent(
                    onRetry = { state.retry() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is TransactionEditViewModel.State.Content -> {
                TransactionEditContent(
                    state = state,
                    onIntent = viewModel::handleIntent,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )

                // Handle bottom sheets and dialogs
                when (state.action) {
                    is TransactionEditViewModel.Action.Loading -> {
                        // Show loading overlay
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is TransactionEditViewModel.Action.ShowAccountBottomSheet -> {

                    }

                    is TransactionEditViewModel.Action.ShowCategoryBottomSheet -> {
                        CategoryBottomSheet(
                            list = state.availableCategories,
                            onDismiss = { viewModel.handleIntent(TransactionEditViewModel.Intent.HideBottomSheet) },
                            onClick = { category ->
                                viewModel.handleIntent(TransactionEditViewModel.Intent.SelectCategory(category))
                            }
                        )
                    }

                    is TransactionEditViewModel.Action.ShowAmountBottomSheet -> {
                        ChangeBalanceBottomSheet(
                            initialValue = state.amount,
                            onDismiss = { viewModel.handleIntent(TransactionEditViewModel.Intent.HideBottomSheet) },
                            onSave = { amount ->
                                viewModel.handleIntent(TransactionEditViewModel.Intent.UpdateAmount(amount))
                            }
                        )
                    }

                    is TransactionEditViewModel.Action.ShowDescriptionBottomSheet -> {
                        ChangeNameBottomSheet(
                            initialValue = state.description,
                            onDismiss = { viewModel.handleIntent(TransactionEditViewModel.Intent.HideBottomSheet) },
                            onSave = { description ->
                                viewModel.handleIntent(TransactionEditViewModel.Intent.UpdateDescription(description))
                            }
                        )
                    }

                    is TransactionEditViewModel.Action.ShowDatePicker -> {
                        YandexFinanceCalendar(
                            onDateSelected = { dateMillis ->
                                dateMillis?.let {
                                    viewModel.handleIntent(TransactionEditViewModel.Intent.UpdateDate(it))
                                }
                            },
                            onDismiss = { viewModel.handleIntent(TransactionEditViewModel.Intent.HideBottomSheet) }
                        )
                    }

                    is TransactionEditViewModel.Action.ShowTimePicker -> {
                        YFTimePicker(
                            onDismiss = { viewModel.handleIntent(TransactionEditViewModel.Intent.HideBottomSheet) },
                            onConfirm = { timePickerState ->
                                viewModel.handleIntent(TransactionEditViewModel.Intent.UpdateTime(timePickerState))
                            }
                        )
                    }

                    else -> {
                        // No action
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.error_text),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            PrimaryButton(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(R.string.retry_text),
                onButtonClick = onRetry
            )
        }
    }
}

@Composable
fun formatDateForDisplay(isoDate: String): String {
    if (isoDate.isEmpty()) return stringResource(R.string.select_date)
    val parts = isoDate.split("-")
    return if (parts.size == 3) {
        "${parts[2]}.${parts[1]}.${parts[0]}"
    } else {
        isoDate
    }
}

@Composable
private fun TransactionEditContent(
    state: TransactionEditViewModel.State.Content,
    onIntent: (TransactionEditViewModel.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ListItemForTransactionEdit(
            isTrailingIconEnabled = true,
            navigationText = stringResource(R.string.account),
            trailingText = state.selectedAccount?.name ?: stringResource(R.string.select_account),
            onClick = { onIntent(TransactionEditViewModel.Intent.ShowAccountSelection) }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        ListItemForTransactionEdit(
            isTrailingIconEnabled = true,
            navigationText = stringResource(R.string.category),
            trailingText = state.selectedCategory?.name ?: stringResource(R.string.select_category),
            onClick = { onIntent(TransactionEditViewModel.Intent.ShowCategorySelection) }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        ListItemForTransactionEdit(
            navigationText = stringResource(R.string.amount),
            trailingText = state.amount.ifEmpty { "0.00" },
            onClick = { onIntent(TransactionEditViewModel.Intent.ShowAmountInput) }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        ListItemForTransactionEdit(
            navigationText = stringResource(R.string.date),
            trailingText = formatDateForDisplay(state.selectedDate),
            onClick = { onIntent(TransactionEditViewModel.Intent.ShowDateSelection) }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        ListItemForTransactionEdit(
            navigationText = stringResource(R.string.time),
            trailingText = state.selectedTime.ifEmpty { stringResource(R.string.select_time) },
            onClick = { onIntent(TransactionEditViewModel.Intent.ShowTimeSelection) }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        ListItemForTransactionEdit(
            navigationText = state.description.ifEmpty { stringResource(R.string.add_description) },
            trailingText = "",
            onClick = { onIntent(TransactionEditViewModel.Intent.ShowDescriptionInput) }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        if (state.isEditing) {
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = if (state.isIncome) stringResource(R.string.delete_income)
                else stringResource(R.string.delete_outcome),
                buttonColors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                onButtonClick = { onIntent(TransactionEditViewModel.Intent.DeleteTransaction) }
            )
        }
    }
}

@Preview
@Composable
private fun TransactionEditScreenContentPreview() {
    TransactionEditContent(
        state = TransactionEditViewModel.State.Content(
            isEditing = true,
            amount = "1000",
            description = "Test transaction",
            selectedDate = "01.01.2024",
            selectedTime = "12:00",
            isIncome = false
        ),
        onIntent = {}
    )
}
