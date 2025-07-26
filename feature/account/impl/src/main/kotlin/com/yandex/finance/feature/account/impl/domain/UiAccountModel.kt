package com.yandex.finance.feature.account.impl.domain

import com.yandex.finance.core.domain.model.CurrencyType

data class UiAccountModel(
    val id: Int,
    val name: String,
    val icon: String?,
    val balance: String,
    val currency: CurrencyType,
    val balanceHistory: List<Int>
) {

    companion object {

        val initial
            get() = UiAccountModel(
                id = 0,
                name = String(),
                balance = "0",
                icon = String(),
                currency = CurrencyType.RUB,
                balanceHistory = listOf()
            )
    }
}
