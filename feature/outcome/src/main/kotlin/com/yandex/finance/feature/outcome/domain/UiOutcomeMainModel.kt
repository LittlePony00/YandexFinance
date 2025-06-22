package com.yandex.finance.feature.outcome.domain

data class UiOutcomeMainModel(
    val sumOfAllOutcomes: Double,
    val outcomes: List<UiOutcomeModel>
) {

    companion object {
        val initial
            get() = UiOutcomeMainModel(
                sumOfAllOutcomes = 0.0,
                outcomes = listOf()
            )
    }
}
