package com.yandex.finance.feature.transaction_edit.impl.presentation.viewmodel

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.data.repository.AccountRepository
import com.yandex.finance.core.data.repository.CategoryRepository
import com.yandex.finance.core.data.repository.TransactionRepository
import com.yandex.finance.core.domain.model.account.AccountDetailed
import com.yandex.finance.core.domain.model.category.Category
import com.yandex.finance.core.domain.model.transaction.TransactionWithoutId
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionEditViewModel @AssistedInject constructor(
    private val accountId: Id,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): TransactionEditViewModel
    }

    private val transactionId: Int = savedStateHandle.get<Int>("transactionId") ?: 0
    private val isEditing: Boolean = savedStateHandle.get<Boolean>("isEdit") ?: false
    private val isIncome: Boolean = savedStateHandle.get<Boolean>("isIncome") ?: false
    private val initialAmount: String = savedStateHandle.get<String>("amount") ?: ""
    private val initialDescription: String = savedStateHandle.get<String>("description") ?: ""
    
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        Timber.d("TransactionId was called $transactionId")
        loadInitialData()
    }

    @Immutable
    sealed interface State {
        data object Loading : State
        
        @JvmInline
        value class Error(val retry: () -> Unit) : State

        data class Content(
            val isEditing: Boolean = false,
            val amount: String = "",
            val description: String = "",
            val selectedAccount: AccountDetailed? = null,
            val selectedCategory: Category? = null,
            val selectedDate: String = "",
            val selectedTime: String = "",
            val availableAccounts: List<AccountDetailed> = emptyList(),
            val availableCategories: List<Category> = emptyList(),
            val isIncome: Boolean = false,
            val action: Action? = null
        ) : State
    }

    @Immutable
    sealed interface Action {
        data object Loading : Action
        data object ShowAccountBottomSheet : Action
        data object ShowCategoryBottomSheet : Action
        data object ShowAmountBottomSheet : Action
        data object ShowDescriptionBottomSheet : Action
        data object ShowDatePicker : Action
        data object ShowTimePicker : Action
    }

    @Immutable
    sealed interface Intent {
        data object LoadData : Intent
        data object ShowAccountSelection : Intent
        data object ShowCategorySelection : Intent
        data object ShowAmountInput : Intent
        data object ShowDescriptionInput : Intent
        data object ShowDateSelection : Intent
        data object ShowTimeSelection : Intent
        data object HideBottomSheet : Intent
        data class SelectAccount(val account: AccountDetailed) : Intent
        data class SelectCategory(val category: Category) : Intent
        data class UpdateAmount(val amount: String) : Intent
        data class UpdateDescription(val description: String) : Intent
        data class UpdateDate(val dateMillis: Long) : Intent
        @OptIn(ExperimentalMaterial3Api::class)
        data class UpdateTime(val timePickerState: TimePickerState) : Intent
        data object SaveTransaction : Intent
        data object DeleteTransaction : Intent
    }

    @Immutable
    sealed interface SideEffect {
        data object NavigateBack : SideEffect
        data object TransactionSaved : SideEffect
        data object TransactionDeleted : SideEffect
        data object ShowError : SideEffect
        data class ShowToast(val message: String) : SideEffect
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun handleIntent(intent: Intent) {
        Timber.d("handleIntent was called. intent: $intent")

        when (intent) {
            is Intent.LoadData -> loadInitialData()
            is Intent.ShowAccountSelection -> showAccountSelection()
            is Intent.ShowCategorySelection -> showCategorySelection()
            is Intent.ShowAmountInput -> showAmountInput()
            is Intent.ShowDescriptionInput -> showDescriptionInput()
            is Intent.ShowDateSelection -> showDateSelection()
            is Intent.ShowTimeSelection -> showTimeSelection()
            is Intent.HideBottomSheet -> hideBottomSheet()
            is Intent.SelectAccount -> selectAccount(intent.account)
            is Intent.SelectCategory -> selectCategory(intent.category)
            is Intent.UpdateAmount -> updateAmount(intent.amount)
            is Intent.UpdateDescription -> updateDescription(intent.description)
            is Intent.UpdateDate -> updateDate(intent.dateMillis)
            is Intent.UpdateTime -> updateTime(intent.timePickerState)
            is Intent.SaveTransaction -> saveTransaction()
            is Intent.DeleteTransaction -> deleteTransaction()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = State.Loading

            try {
                val accountsResult = accountRepository.fetchAccounts()
                val accounts = accountsResult.getOrThrow()

                val categoriesResult = if (isEditing) {
                    categoryRepository.fetchCategories()
                } else {
                    categoryRepository.fetchCategoriesByType(isIncome)
                }
                val categories = categoriesResult.getOrThrow()

                if (isEditing && transactionId != 0) {
                    val transactionResult = transactionRepository.fetchTransactionById(transactionId)
                    val transaction = transactionResult.getOrThrow()

                    val selectedAccount = accounts.find { it.id == transaction.account.id }
                    val selectedCategory = categories.find { it.id == transaction.category.id }

                    val contentState = State.Content(
                        isEditing = isEditing,
                        amount = transaction.amount,
                        description = transaction.comment ?: "",
                        availableAccounts = accounts,
                        availableCategories = categories,
                        isIncome = transaction.category.isIncome,
                        selectedAccount = selectedAccount,
                        selectedCategory = selectedCategory,
                        selectedDate = extractDateFromIsoString(transaction.transactionDate),
                        selectedTime = extractTimeFromIsoString(transaction.transactionDate)
                    )

                    _uiState.value = contentState
                } else {
                    val contentState = State.Content(
                        isEditing = isEditing,
                        amount = initialAmount,
                        description = initialDescription,
                        availableAccounts = accounts,
                        availableCategories = categories,
                        isIncome = isIncome,
                        selectedAccount = accounts.firstOrNull(),
                        selectedDate = getCurrentDateIso(),
                        selectedTime = getCurrentTimeString()
                    )

                    _uiState.value = contentState
                }
            } catch (e: Exception) {
                Timber.e(e, "Error loading initial data")
                _uiState.value = State.Error(retry = { loadInitialData() })
            }
        }
    }

    private fun showAccountSelection() {
        updateContentState { content ->
            content.copy(action = Action.ShowAccountBottomSheet)
        }
    }

    private fun showCategorySelection() {
        updateContentState { content ->
            content.copy(action = Action.ShowCategoryBottomSheet)
        }
    }

    private fun showAmountInput() {
        updateContentState { content ->
            content.copy(action = Action.ShowAmountBottomSheet)
        }
    }

    private fun showDescriptionInput() {
        updateContentState { content ->
            content.copy(action = Action.ShowDescriptionBottomSheet)
        }
    }

    private fun showDateSelection() {
        updateContentState { content ->
            content.copy(action = Action.ShowDatePicker)
        }
    }

    private fun showTimeSelection() {
        updateContentState { content ->
            content.copy(action = Action.ShowTimePicker)
        }
    }

    private fun hideBottomSheet() {
        updateContentState { content ->
            content.copy(action = null)
        }
    }

    private fun selectAccount(account: AccountDetailed) {
        updateContentState { content ->
            content.copy(selectedAccount = account, action = null)
        }
    }

    private fun selectCategory(category: Category) {
        updateContentState { content ->
            content.copy(selectedCategory = category, action = null)
        }
    }

    private fun updateAmount(amount: String) {
        if (amount.isBlank()) {
            Timber.d("Amount validation failed: empty")
            return
        }

        try {
            val amountValue = amount.toDoubleOrNull()
            if (amountValue == null || amountValue <= 0) {
                Timber.d("Amount validation failed: invalid format")
                return
            }
        } catch (e: NumberFormatException) {
            Timber.d("Amount validation failed: number format exception")
            return
        }
        
        updateContentState { content ->
            content.copy(amount = amount, action = null)
        }
    }

    private fun updateDescription(description: String) {
        if (description.length > 255) {
            Timber.d("Description validation failed: too long")
            return
        }
        
        updateContentState { content ->
            content.copy(description = description, action = null)
        }
    }

    private fun updateDate(dateMillis: Long) {
        val dateString = formatDateFromMillisForDisplay(dateMillis)
        val isoDate = formatDateFromMillisForIso(dateMillis)
        updateContentState { content ->
            content.copy(selectedDate = isoDate, action = null)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun updateTime(timePickerState: TimePickerState) {
        val timeString = formatTime(timePickerState.hour, timePickerState.minute)
        updateContentState { content ->
            content.copy(selectedTime = timeString, action = null)
        }
    }

    private fun saveTransaction() {
        Timber.d("saveTransaction was called")

        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState !is State.Content) return@launch

            if (currentState.amount.isBlank()) {
                _sideEffect.send(SideEffect.ShowToast("Введите сумму"))
                return@launch
            }

            if (currentState.selectedAccount == null) {
                _sideEffect.send(SideEffect.ShowToast("Выберите счёт"))
                return@launch
            }

            if (currentState.selectedCategory == null) {
                _sideEffect.send(SideEffect.ShowToast("Выберите категорию"))
                return@launch
            }

            updateContentState { content ->
                content.copy(action = Action.Loading)
            }

            try {
                Timber.d("Creating transaction with accountId: ${currentState.selectedAccount.id}, accountName: ${currentState.selectedAccount.name}")
                val transactionData = TransactionWithoutId(
                    accountId = accountId.toInt(),
                    amount = currentState.amount,
                    categoryId = currentState.selectedCategory.id,
                    comment = currentState.description.ifBlank { "" },
                    transactionDate = combineDateTime(currentState.selectedDate, currentState.selectedTime)
                )

                if (isEditing) {
                    val result = transactionRepository.updateTransaction(transactionId, transactionData)
                    result.fold(
                        onSuccess = {
                            Timber.d("Transaction updated successfully")
                            updateContentState { content ->
                                content.copy(action = null)
                            }
                            _sideEffect.send(SideEffect.TransactionSaved)
                            _sideEffect.send(SideEffect.NavigateBack)
                            Timber.d("NavigateBack side effect sent for update")
                        },
                        onFailure = { throwable ->
                            Timber.e(throwable, "Error updating transaction")
                            _sideEffect.send(SideEffect.ShowError)
                            updateContentState { content ->
                                content.copy(action = null)
                            }
                        }
                    )
                } else {
                    val result = transactionRepository.createTransaction(transactionData)
                    result.fold(
                        onSuccess = {
                            Timber.d("Transaction created successfully")
                            updateContentState { content ->
                                content.copy(action = null)
                            }
                            _sideEffect.send(SideEffect.TransactionSaved)
                            _sideEffect.send(SideEffect.NavigateBack)
                            Timber.d("NavigateBack side effect sent for create")
                        },
                        onFailure = { throwable ->
                            Timber.e(throwable, "Error creating transaction")
                            _sideEffect.send(SideEffect.ShowError)
                            updateContentState { content ->
                                content.copy(action = null)
                            }
                        }
                    )
                }
            } catch (e: Exception) {
                Timber.e(e, "Error saving transaction")
                _sideEffect.send(SideEffect.ShowError)
                updateContentState { content ->
                    content.copy(action = null)
                }
            }
        }
    }

    private fun deleteTransaction() {
        if (!isEditing) return

        viewModelScope.launch {
            updateContentState { content ->
                content.copy(action = Action.Loading)
            }

            try {
                val result = transactionRepository.deleteTransaction(transactionId)
                result.fold(
                    onSuccess = {
                        Timber.d("Transaction deleted successfully")
                        updateContentState { content ->
                            content.copy(action = null)
                        }
                        _sideEffect.send(SideEffect.TransactionDeleted)
                        _sideEffect.send(SideEffect.NavigateBack)
                        Timber.d("NavigateBack side effect sent for delete")
                    },
                    onFailure = { throwable ->
                        Timber.e(throwable, "Error deleting transaction")
                        _sideEffect.send(SideEffect.ShowError)
                        updateContentState { content ->
                            content.copy(action = null)
                        }
                    }
                )
            } catch (e: Exception) {
                Timber.e(e, "Error deleting transaction")
                _sideEffect.send(SideEffect.ShowError)
                updateContentState { content ->
                    content.copy(action = null)
                }
            }
        }
    }

    private fun updateContentState(update: (State.Content) -> State.Content) {
        val currentState = _uiState.value
        if (currentState is State.Content) {
            _uiState.value = update(currentState)
        }
    }

    private fun getCurrentDateString(): String {
        val now = Clock.System.now()
        return formatDateFromInstant(now)
    }

    private fun getCurrentDateIso(): String {
        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.year}-${localDateTime.monthNumber.toString().padStart(2, '0')}-${localDateTime.dayOfMonth.toString().padStart(2, '0')}"
    }

    private fun getCurrentTimeString(): String {
        val now = Date()
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(now)
    }

    private fun formatDateFromMillisForDisplay(dateMillis: Long): String {
        val date = Date(dateMillis)
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return formatter.format(date)
    }

    private fun formatDateFromMillisForIso(dateMillis: Long): String {
        val date = Date(dateMillis)
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(date)
    }

    private fun formatDateFromInstant(instant: Instant): String {
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.dayOfMonth.toString().padStart(2, '0')}.${localDateTime.monthNumber.toString().padStart(2, '0')}.${localDateTime.year}"
    }

    private fun formatTime(hour: Int, minute: Int): String {
        return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
    }

    private fun combineDateTime(dateString: String, timeString: String): String {
        return try {
            // dateString is in ISO format (yyyy-MM-dd), timeString is in HH:mm format
            val timeParts = timeString.split(":")
            val hour = timeParts[0].toInt()
            val minute = timeParts[1].toInt()
            
            val dateParts = dateString.split("-")
            val year = dateParts[0].toInt()
            val month = dateParts[1].toInt()
            val day = dateParts[2].toInt()
            
            // Create local date time
            val localDateTime = LocalDateTime(year, month, day, hour, minute, 0)
            
            // Convert to UTC instant
            val instant = localDateTime.toInstant(TimeZone.currentSystemDefault())
            
            // Return as ISO string in UTC
            instant.toString()
        } catch (e: Exception) {
            Timber.w(e, "Failed to combine date and time: $dateString, $timeString")
            // Fallback to old method if parsing fails
            "${dateString}T${timeString}:00Z"
        }
    }

    private fun formatDateForDisplay(isoDate: String): String {
        // Convert from yyyy-MM-dd to dd.MM.yyyy for display
        val parts = isoDate.split("-")
        if (parts.size == 3) {
            return "${parts[2]}.${parts[1]}.${parts[0]}"
        }
        return isoDate
    }

    private fun extractTimeFromIsoString(isoDateString: String): String {
        return try {
            val instant = Instant.parse(isoDateString)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
        } catch (e: Exception) {
            Timber.w(e, "Failed to extract time from ISO string: $isoDateString")
            getCurrentTimeString()
        }
    }

    private fun extractDateFromIsoString(isoDateString: String): String {
        return try {
            val instant = Instant.parse(isoDateString)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            "${localDateTime.year}-${localDateTime.monthNumber.toString().padStart(2, '0')}-${localDateTime.dayOfMonth.toString().padStart(2, '0')}"
        } catch (e: Exception) {
            Timber.w(e, "Failed to extract date from ISO string: $isoDateString")
            getCurrentDateIso()
        }
    }
}
