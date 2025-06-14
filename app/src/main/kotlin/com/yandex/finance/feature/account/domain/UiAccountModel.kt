package com.yandex.finance.feature.account.domain

data class UiAccountModel(
    val id: Int,
    val balance: Int,
    val icon: String?,
    val currency: CurrencyType,
) {

    companion object {

        val initial
            get() = UiAccountModel(
                id = 0,
                balance = 0,
                icon = String(),
                currency = CurrencyType.RUB
            )
    }
}

enum class CurrencyType {
    RUB,
    USD,
    EUR,
}
