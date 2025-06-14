package com.yandex.finance.feature.outcome.domain

data class UiOutcomeTodayModel(
    val sumOfAllOutcomes: Int,
    val outcomes: List<UiOutcomeModel>
) {

    companion object {
        val initial
            get() = UiOutcomeTodayModel(
                sumOfAllOutcomes = 100000,
                outcomes = listOf()
            )
    }
}
