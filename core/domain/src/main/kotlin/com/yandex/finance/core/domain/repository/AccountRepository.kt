package com.yandex.finance.core.domain.repository

import com.yandex.finance.core.domain.model.account.*

interface AccountRepository {

    suspend fun fetchAccounts(): Result<List<AccountDetailed>>

    suspend fun createAccount(body: AccountWithoutId): Result<AccountDetailed>

    suspend fun fetchAccount(id: String): Result<MainAccount>

    suspend fun updateAccount(
        id: String,
        body: AccountWithoutId
    ): Result<AccountDetailed>

    suspend fun deleteAccount(id: String): Result<Boolean>

    suspend fun fetchAccountHistory(id: String): Result<AccountHistory>
}
