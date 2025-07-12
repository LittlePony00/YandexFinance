package com.yandex.finance.feature.transaction_edit.api.navigation

import com.yandex.finance.core.domain.model.CurrencyType
import com.yandex.finance.core.ui.navigation.Graph
import kotlinx.serialization.Serializable

@Serializable
object TransactionEditGraph : Graph

sealed interface TransactionEditFlow {

    @Serializable
    data class EditTransaction(
        val transactionId: Int,
        val amount: String,
        val isEdit: Boolean,
        val isIncome: Boolean,
        val description: String,
        val currencyType: CurrencyType
    ) : TransactionEditFlow
}
