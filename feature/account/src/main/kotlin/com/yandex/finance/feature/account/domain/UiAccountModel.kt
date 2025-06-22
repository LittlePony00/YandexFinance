package com.yandex.finance.feature.account.domain

import com.yandex.finance.core.domain.model.CurrencyType

data class UiAccountModel(
    val id: Int,
    val balance: String,
    val icon: String?,
    val currency: CurrencyType,
) {

    companion object {

        val initial
            get() = UiAccountModel(
                id = 0,
                balance = "0",
                icon = String(),
                currency = CurrencyType.RUB
            )
    }
}
