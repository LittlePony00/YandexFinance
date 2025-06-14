package com.yandex.finance.feature.settings.presentation.navigation

import com.yandex.finance.core.ui.Graph
import kotlinx.serialization.Serializable

@Serializable
object SettingsGraph : Graph

sealed interface SettingsFlow {

    @Serializable
    data object Settings : SettingsFlow
}
