package com.yandex.finance.feature.settings.api.navigation

import com.yandex.finance.core.ui.navigation.Graph
import kotlinx.serialization.Serializable

@Serializable
object SettingsGraph : Graph

sealed interface SettingsFlow {

    @Serializable
    data object Settings : SettingsFlow
    
    @Serializable
    data object ColorPicker : SettingsFlow
    
    @Serializable
    data object HapticsSettings : SettingsFlow
    
    @Serializable
    data object PinCode : SettingsFlow
    
    @Serializable
    data object SyncFrequency : SettingsFlow
    
    @Serializable
    data object Language : SettingsFlow
}
