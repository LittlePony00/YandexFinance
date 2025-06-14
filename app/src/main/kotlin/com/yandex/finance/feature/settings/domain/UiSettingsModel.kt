package com.yandex.finance.feature.settings.domain

data class UiSettingsModel(
    val isDarkMode: Boolean,
    val isSoundEnabled: Boolean,
    val isHapticEnabled: Boolean,
) {

    companion object {
        val initial
            get() = UiSettingsModel(
                isDarkMode = false,
                isSoundEnabled = false,
                isHapticEnabled = false,
            )
    }
}
