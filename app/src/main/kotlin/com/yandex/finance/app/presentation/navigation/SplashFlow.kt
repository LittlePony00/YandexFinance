package com.yandex.finance.app.presentation.navigation

import com.yandex.finance.core.ui.navigation.Graph
import kotlinx.serialization.Serializable

@Serializable
object SplashGraph : Graph

sealed interface SplashFlow {

    @Serializable
    data object SplashScreen : SplashFlow
}
