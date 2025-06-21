package com.yandex.finance.core.navigation.splash

import com.yandex.finance.core.navigation.Graph
import kotlinx.serialization.Serializable

@Serializable
object SplashGraph : Graph

sealed interface SplashFlow {

    @Serializable
    data object SplashScreen : SplashFlow
}
