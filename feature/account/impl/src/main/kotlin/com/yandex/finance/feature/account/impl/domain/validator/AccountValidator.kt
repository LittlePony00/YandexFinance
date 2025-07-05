package com.yandex.finance.feature.account.impl.domain.validator

import com.yandex.finance.feature.account.impl.R
import java.math.BigDecimal

internal class AccountValidator {
    
    sealed class ValidationError {
        data object AccountNameEmpty : ValidationError()
        data object AccountNameTooShort : ValidationError()
        data object AccountNameTooLong : ValidationError()
        data object BalanceEmpty : ValidationError()
        data object BalanceInvalidFormat : ValidationError()
        data object BalanceNegative : ValidationError()
        data object BalanceTooLarge : ValidationError()
        data object BalanceInvalidAmount : ValidationError()
    }
    
    fun validateAccountName(name: String): ValidationError? {
        return when {
            name.isBlank() -> ValidationError.AccountNameEmpty
            name.length < 2 -> ValidationError.AccountNameTooShort
            name.length > 50 -> ValidationError.AccountNameTooLong
            else -> null
        }
    }
    
    fun validateBalance(balance: String): ValidationError? {
        return when {
            balance.isBlank() -> ValidationError.BalanceEmpty
            !balance.matches(Regex("^\\d+(\\.\\d{1,2})?$")) -> ValidationError.BalanceInvalidFormat
            else -> {
                try {
                    val amount = BigDecimal(balance)
                    when {
                        amount < BigDecimal.ZERO -> ValidationError.BalanceNegative
                        amount > BigDecimal("999999999.99") -> ValidationError.BalanceTooLarge
                        else -> null
                    }
                } catch (e: Exception) {
                    ValidationError.BalanceInvalidAmount
                }
            }
        }
    }
}

internal fun AccountValidator.ValidationError.validationErrorToString(): Int {
    return when (this) {
        AccountValidator.ValidationError.AccountNameEmpty -> R.string.validation_account_name_empty
        AccountValidator.ValidationError.AccountNameTooShort -> R.string.validation_account_name_too_short
        AccountValidator.ValidationError.AccountNameTooLong -> R.string.validation_account_name_too_long
        AccountValidator.ValidationError.BalanceEmpty -> R.string.validation_balance_empty
        AccountValidator.ValidationError.BalanceInvalidFormat -> R.string.validation_balance_invalid_format
        AccountValidator.ValidationError.BalanceNegative -> R.string.validation_balance_negative
        AccountValidator.ValidationError.BalanceTooLarge -> R.string.validation_balance_too_large
        AccountValidator.ValidationError.BalanceInvalidAmount -> R.string.validation_balance_invalid_amount
    }
}
