package com.yandex.finance.feature.account.impl.presentation.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.data.repository.AccountRepository
import com.yandex.finance.core.domain.model.CurrencyType
import com.yandex.finance.core.domain.model.account.AccountWithoutId
import com.yandex.finance.feature.account.impl.domain.UiAccountModel
import com.yandex.finance.core.ui.validator.AccountValidator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MyAccountEditViewModel @AssistedInject constructor(
    private val accountRepository: AccountRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): MyAccountEditViewModel
    }

    private val accountValidator = AccountValidator()

    private val accountId: Int = savedStateHandle.get<Int>("id") ?: 0
    private val accountName: String = savedStateHandle.get<String>("name") ?: ""
    private val accountIcon: String = savedStateHandle.get<String?>("icon") ?: "💰"
    private val accountBalance: String = savedStateHandle.get<String>("balance") ?: "0.00"
    private val currencyType: CurrencyType = savedStateHandle.get<CurrencyType>("currencyType") ?: CurrencyType.RUB
    
    private val uiAccountModel = UiAccountModel(
        id = accountId,
        name = accountName,
        icon = accountIcon,
        balance = accountBalance,
        currency = currencyType
    )

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(
        State.Content(
            accountData = uiAccountModel,
            tempAccountName = uiAccountModel.name,
            tempBalance = uiAccountModel.balance,
            tempCurrency = uiAccountModel.currency
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    @Immutable
    sealed interface State {
        data object Error : State

        data class Content(
            val accountData: UiAccountModel,
            val tempAccountName: String,
            val tempBalance: String,
            val tempCurrency: CurrencyType,
            val action: Action? = null
        ) : State
    }

    @Immutable
    sealed interface Action {
        data object Loading : Action
        data object ShowCurrencyDialog : Action
        data object ShowAccountNameDialog : Action
        data object ShowBalanceDialog : Action
    }

    @Immutable
    sealed interface Intent {
        data object ShowCurrencyBottomSheet : Intent
        data object ShowAccountNameBottomSheet : Intent
        data object ShowBalanceBottomSheet : Intent
        data object HideBottomSheet : Intent
        data object DeleteAccount : Intent
        data object SaveChanges : Intent
        data class SaveAccountNameChanges(val name: String) : Intent
        data class SaveBalanceChanges(val balance: String) : Intent
        data class SelectCurrency(val currency: CurrencyType) : Intent
    }

    @Immutable
    sealed interface SideEffect {
        data object NavigateBack : SideEffect
        data object AccountDeleted : SideEffect
        data object AccountSaved : SideEffect
        data object ShowNetworkError : SideEffect
        data object ShowDeleteError : SideEffect
        data object ShowSaveError : SideEffect
    }

    fun handleIntent(intent: Intent) {
        Timber.d("handleIntent was called. intent: $intent")

        when (intent) {
            is Intent.ShowCurrencyBottomSheet -> showCurrencyBottomSheet()
            is Intent.ShowAccountNameBottomSheet -> showAccountNameBottomSheet()
            is Intent.ShowBalanceBottomSheet -> showBalanceBottomSheet()
            is Intent.HideBottomSheet -> hideBottomSheet()
            is Intent.SelectCurrency -> selectCurrency(intent.currency)
            is Intent.SaveChanges -> saveChanges()
            is Intent.SaveAccountNameChanges -> saveAccountNameChanges(intent.name)
            is Intent.SaveBalanceChanges -> saveBalanceChanges(intent.balance)
            is Intent.DeleteAccount -> deleteAccount()
        }
    }

    private fun showCurrencyBottomSheet() {
        Timber.d("showCurrencyBottomSheet was called")

        updateContentState { content ->
            content.copy(action = Action.ShowCurrencyDialog)
        }
    }

    private fun showAccountNameBottomSheet() {
        Timber.d("showAccountNameBottomSheet was called")

        updateContentState { content ->
            content.copy(action = Action.ShowAccountNameDialog)
        }
    }

    private fun showBalanceBottomSheet() {
        Timber.d("showBalanceBottomSheet was called")

        updateContentState { content ->
            content.copy(action = Action.ShowBalanceDialog)
        }
    }

    private fun hideBottomSheet() {
        Timber.d("hideBottomSheet was called")

        updateContentState { content ->
            content.copy(action = null)
        }
    }

    private fun selectCurrency(currency: CurrencyType) {
        Timber.d("selectCurrency was called. currency: $currency")

        updateContentState { content ->
            content.copy(tempCurrency = currency, action = null)
        }
    }

    private fun saveChanges() {
        Timber.d("saveChanges was called")

        viewModelScope.launch {
            updateContentState { content ->
                content.copy(action = Action.Loading)
            }

            val currentState = _uiState.value
            if (currentState is State.Content) {
                val accountWithoutId = AccountWithoutId(
                    name = currentState.tempAccountName,
                    balance = currentState.tempBalance,
                    currency = currentState.tempCurrency.name
                )

                val result = accountRepository.updateAccount(
                    id = currentState.accountData.id.toString(),
                    body = accountWithoutId
                )

                result.fold(
                    onSuccess = {
                        _sideEffect.send(SideEffect.AccountSaved)
                        _sideEffect.send(SideEffect.NavigateBack)
                    },
                    onFailure = { throwable ->
                        Timber.e(throwable, "Error saving account")
                        _sideEffect.send(SideEffect.ShowSaveError)
                    }
                )
            }

            updateContentState { content ->
                content.copy(action = null)
            }
        }
    }

    private fun deleteAccount() {
        Timber.d("deleteAccount was called")

        viewModelScope.launch {
            updateContentState { content ->
                content.copy(action = Action.Loading)
            }

            val currentState = _uiState.value
            if (currentState is State.Content) {
                val result = accountRepository.deleteAccount(currentState.accountData.id.toString())

                result.fold(
                    onSuccess = {
                        _sideEffect.send(SideEffect.AccountDeleted)
                        _sideEffect.send(SideEffect.NavigateBack)
                    },
                    onFailure = { throwable ->
                        Timber.e(throwable, "Error deleting account")
                        _sideEffect.send(SideEffect.ShowDeleteError)
                    }
                )
            }

            updateContentState { content ->
                content.copy(action = null)
            }
        }
    }

    private fun updateContentState(block: (content: State.Content) -> State.Content) {
        val state = _uiState.value
        if (state is State.Content) {
            _uiState.value = block(state)
        }
    }

    private fun saveAccountNameChanges(name: String) {
        Timber.d("saveAccountNameChanges was called. name: $name")

        val validationError = accountValidator.validateAccountName(name)
        if (validationError != null) {
            Timber.d("Validation error for account name: $validationError")
            return
        }
        
        updateContentState { content ->
            content.copy(
                tempAccountName = name,
                action = null
            )
        }
    }

    private fun saveBalanceChanges(balance: String) {
        Timber.d("saveBalanceChanges was called. balance: $balance")

        val validationError = accountValidator.validateBalance(balance)
        if (validationError != null) {
            Timber.d("Validation error for balance: $validationError")
            return
        }
        
        updateContentState { content ->
            content.copy(
                tempBalance = balance,
                action = null
            )
        }
    }
}
