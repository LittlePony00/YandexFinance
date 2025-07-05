package com.yandex.finance.core.domain.model

/**
 * Currency type with display symbol
 * 
 * @param type Currency symbol for UI display
 */
enum class CurrencyType(val type: String) {
    /** Russian ruble */
    RUB(type = "₽"),
    /** US dollar */
    USD(type ="$"),
    /** Euro */
    EUR(type = "€"),
    ;

    companion object {

        /**
         * Converts string representation of currency to enum
         * 
         * @param type String representation of currency
         * @return Corresponding currency type or RUB as default
         */
        fun convertFromString(type: String): CurrencyType {
            return when(type) {
                "USD" -> USD
                "EUR" -> EUR
                else -> RUB
            }
        }
    }
}
