package com.yandex.finance.feature.account.domain

import kotlin.reflect.typeOf

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

enum class CurrencyType(val type: String) {
    RUB(type = "₽"),
    USD(type ="$"),
    EUR(type = "€"),
    ;

    companion object {

        fun convertFromString(type: String): CurrencyType {
            return when(type) {
                "USD" -> USD
                "EUR" -> EUR
                else -> RUB
            }
        }
    }
}
