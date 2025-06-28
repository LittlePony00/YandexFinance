package com.yandex.finance.feature.settings.api.navigation

import com.yandex.finance.core.ui.navigation.Graph
import kotlinx.serialization.Serializable

@Serializable
object SettingsGraph : Graph

sealed interface SettingsFlow {

    @Serializable
    data object Settings : SettingsFlow
}
