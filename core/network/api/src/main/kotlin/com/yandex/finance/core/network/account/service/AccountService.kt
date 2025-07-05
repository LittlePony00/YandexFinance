package com.yandex.finance.core.network.account.service

import com.yandex.finance.core.network.account.model.NetworkAccountDetailed
import com.yandex.finance.core.network.account.model.NetworkAccountHistory
import com.yandex.finance.core.network.account.model.NetworkAccountWithoutId
import com.yandex.finance.core.network.account.model.NetworkMainAccount

interface AccountService {

    suspend fun fetchAccounts(): Result<List<NetworkAccountDetailed>>

    suspend fun createAccount(body: NetworkAccountWithoutId): Result<NetworkAccountDetailed>

    suspend fun fetchAccount(id: String): Result<NetworkMainAccount>

    suspend fun updateAccount(
        id: String,
        body: NetworkAccountWithoutId
    ): Result<NetworkAccountDetailed>

    suspend fun deleteAccount(id: String): Result<Unit>

    suspend fun fetchAccountHistory(id: String): Result<NetworkAccountHistory>
}
