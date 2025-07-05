package com.yandex.finance.core.data.repository

import com.yandex.finance.core.data.mapper.asExternalModel
import com.yandex.finance.core.data.mapper.asAnotherResult
import com.yandex.finance.core.data.mapper.asNetworkModel
import com.yandex.finance.core.domain.model.account.*
import com.yandex.finance.core.network.account.service.AccountService

class AccountRepositoryImpl(
    private val accountService: AccountService
) : AccountRepository {

    override suspend fun fetchAccounts(): Result<List<AccountDetailed>> {
        return accountService.fetchAccounts().asAnotherResult { accountList ->
            accountList.map { it.asExternalModel() }
        }
    }

    override suspend fun createAccount(body: AccountWithoutId): Result<AccountDetailed> {
        return accountService.createAccount(body.asNetworkModel()).asAnotherResult {
            it.asExternalModel() 
        }
    }

    override suspend fun fetchAccount(id: String): Result<MainAccount> {
        return accountService.fetchAccount(id).asAnotherResult {
            it.asExternalModel() 
        }
    }

    override suspend fun updateAccount(
        id: String,
        body: AccountWithoutId
    ): Result<AccountDetailed> {
        return accountService.updateAccount(id, body.asNetworkModel()).asAnotherResult {
            it.asExternalModel() 
        }
    }

    override suspend fun deleteAccount(id: String): Result<Unit> {
        return accountService.deleteAccount(id)
    }

    override suspend fun fetchAccountHistory(id: String): Result<AccountHistory> {
        return accountService.fetchAccountHistory(id).asAnotherResult {
            it.asExternalModel() 
        }
    }
}
