package com.yandex.finance.core.domain.model

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
