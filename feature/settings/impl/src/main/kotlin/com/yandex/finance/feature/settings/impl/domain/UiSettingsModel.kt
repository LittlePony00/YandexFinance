package com.yandex.finance.feature.settings.impl.domain

data class UiSettingsModel(
    val isDarkMode: Boolean,
    val chapters: List<UiChapterModel>,
    val lastSyncTime: String
) {

    companion object {
        val initial
            get() = UiSettingsModel(
                isDarkMode = false,
                chapters = listOf(),
                lastSyncTime = "Никогда"
            )
    }
}

data class UiChapterModel(
    val title: String,
    val icon: String? = null,
)
