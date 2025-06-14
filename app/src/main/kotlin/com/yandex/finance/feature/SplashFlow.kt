package com.yandex.finance.feature

import com.yandex.finance.core.ui.Graph
import kotlinx.serialization.Serializable

@Serializable
object SplashGraph : Graph

sealed interface SplashFlow {

    @Serializable
    data object SplashScreen : SplashFlow
}
