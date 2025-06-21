package com.yandex.finance.core.navigation.settings

import kotlinx.serialization.Serializable

@Serializable
object SettingsGraph : com.yandex.finance.core.navigation.Graph

sealed interface SettingsFlow {

    @Serializable
    data object Settings : SettingsFlow
}
