package com.yandex.finance.feature.account.api.navigation

import com.yandex.finance.core.ui.navigation.Graph
import kotlinx.serialization.Serializable

@Serializable
object AccountGraph : Graph

sealed interface AccountFlow {

    @Serializable
    data object MyAccount : AccountFlow
}
