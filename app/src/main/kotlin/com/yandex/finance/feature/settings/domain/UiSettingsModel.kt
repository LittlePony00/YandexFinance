package com.yandex.finance.feature.settings.domain

data class UiSettingsModel(
    val isDarkMode: Boolean,
    val chapters: List<UiChapterModel>
) {

    companion object {
        val initial
            get() = UiSettingsModel(
                isDarkMode = false,
                chapters = listOf()
            )
    }
}

data class UiChapterModel(
    val title: String,
    val icon: String? = null,
)
