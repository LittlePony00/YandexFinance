package com.yandex.finance.core.localdb.mapper

import com.yandex.finance.core.domain.model.account.*
import com.yandex.finance.core.domain.model.category.Category
import com.yandex.finance.core.domain.model.transaction.*
import com.yandex.finance.core.localdb.entity.*

// Transaction mappers
fun TransactionEntity.toDomainModel(): Transaction {
    return Transaction(
        id = id,
        amount = amount,
        comment = comment,
        account = Account(
            id = accountId,
            name = "",
            balance = "",
            currency = ""
        ),
        createdAt = createdAt,
        updatedAt = updatedAt,
        category = Category(
            id = categoryId,
            name = "",
            emoji = null,
            isIncome = false
        ),
        transactionDate = transactionDate
    )
}

fun Transaction.toEntity(
    isLocalOnly: Boolean = false,
    isPendingSync: Boolean = false,
    localCreatedAt: Long = System.currentTimeMillis(),
    lastSyncAt: Long? = null,
    syncVersion: Int = 1
): TransactionEntity {
    return TransactionEntity(
        id = id,
        amount = amount,
        comment = comment,
        accountId = account.id,
        categoryId = category.id,
        transactionDate = transactionDate,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isLocalOnly = isLocalOnly,
        isPendingSync = isPendingSync,
        localCreatedAt = localCreatedAt,
        lastSyncAt = lastSyncAt,
        syncVersion = syncVersion
    )
}

fun TransactionWithoutId.toEntity(
    id: Int = 0,
    isLocalOnly: Boolean = true,
    isPendingSync: Boolean = true,
    localCreatedAt: Long = System.currentTimeMillis(),
    createdAt: String = "",
    updatedAt: String = ""
): TransactionEntity {
    return TransactionEntity(
        id = id,
        amount = amount,
        comment = comment,
        accountId = accountId,
        categoryId = categoryId,
        transactionDate = transactionDate,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isLocalOnly = isLocalOnly,
        isPendingSync = isPendingSync,
        localCreatedAt = localCreatedAt,
        lastSyncAt = null,
        syncVersion = 1
    )
}

// Account mappers
fun AccountEntity.toDomainModel(): AccountDetailed {
    return AccountDetailed(
        id = id,
        userId = userId ?: 0,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun AccountEntity.toBasicAccount(): Account {
    return Account(
        id = id,
        name = name,
        balance = balance,
        currency = currency
    )
}

fun AccountEntity.toDetailedAccount(): AccountDetailed {
    return AccountDetailed(
        id = id,
        userId = userId ?: 0,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun MainAccount.toEntity(
    isLocalOnly: Boolean = false,
    isPendingSync: Boolean = false,
    localCreatedAt: Long = System.currentTimeMillis(),
    lastSyncAt: Long? = null,
    syncVersion: Int = 1
): AccountEntity {
    return AccountEntity(
        id = id,
        userId = null,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isLocalOnly = isLocalOnly,
        isPendingSync = isPendingSync,
        localCreatedAt = localCreatedAt,
        lastSyncAt = lastSyncAt,
        syncVersion = syncVersion
    )
}

fun AccountDetailed.toEntity(
    isLocalOnly: Boolean = false,
    isPendingSync: Boolean = false,
    localCreatedAt: Long = System.currentTimeMillis(),
    lastSyncAt: Long? = null,
    syncVersion: Int = 1
): AccountEntity {
    return AccountEntity(
        id = id,
        userId = userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isLocalOnly = isLocalOnly,
        isPendingSync = isPendingSync,
        localCreatedAt = localCreatedAt,
        lastSyncAt = lastSyncAt,
        syncVersion = syncVersion
    )
}

fun AccountWithoutId.toEntity(
    id: Int = 0,
    userId: Int? = null,
    isLocalOnly: Boolean = true,
    isPendingSync: Boolean = true,
    localCreatedAt: Long = System.currentTimeMillis(),
    createdAt: String = "",
    updatedAt: String = ""
): AccountEntity {
    return AccountEntity(
        id = id,
        userId = userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isLocalOnly = isLocalOnly,
        isPendingSync = isPendingSync,
        localCreatedAt = localCreatedAt,
        lastSyncAt = null,
        syncVersion = 1
    )
}

// Category mappers
fun CategoryEntity.toDomainModel(): Category {
    return Category(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}

fun Category.toEntity(
    createdAt: String? = null,
    updatedAt: String? = null,
    lastSyncAt: Long? = null
): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome,
        createdAt = createdAt,
        updatedAt = updatedAt,
        lastSyncAt = lastSyncAt
    )
} 