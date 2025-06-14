package com.yandex.finance.feature.account.presentation.navigation

import com.yandex.finance.core.ui.Graph
import kotlinx.serialization.Serializable

@Serializable
object AccountGraph : Graph

sealed interface AccountFlow {

    @Serializable
    data object MyAccount : AccountFlow
}
