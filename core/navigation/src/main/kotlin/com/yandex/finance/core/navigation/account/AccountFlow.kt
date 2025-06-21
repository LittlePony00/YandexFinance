package com.yandex.finance.core.navigation.account

import com.yandex.finance.core.navigation.Graph
import kotlinx.serialization.Serializable

@Serializable
object AccountGraph : Graph

sealed interface AccountFlow {

    @Serializable
    data object MyAccount : AccountFlow
}
