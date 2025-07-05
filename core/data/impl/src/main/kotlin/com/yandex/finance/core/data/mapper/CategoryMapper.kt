package com.yandex.finance.core.data.mapper

import com.yandex.finance.core.domain.model.category.Category
import com.yandex.finance.core.network.category.model.NetworkCategory

// Domain to Network mappers
fun Category.asNetworkModel() = NetworkCategory(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)

// Network to Domain mappers
fun NetworkCategory.asExternalModel() = Category(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)
