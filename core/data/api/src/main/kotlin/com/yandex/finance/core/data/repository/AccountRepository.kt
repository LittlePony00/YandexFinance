package com.yandex.finance.core.data.repository

import com.yandex.finance.core.domain.model.account.*

/**
 * Interface for work with user account
 */
interface AccountRepository {

    /**
     * Fetches all accounts
     *
     * @return Result<List<[AccountDetailed]>>
     */
    suspend fun fetchAccounts(): Result<List<AccountDetailed>>

    /**
     * Creates a new account
     *
     * @param body [AccountWithoutId]
     *
     * @return Result<[AccountDetailed]>
     */
    suspend fun createAccount(body: AccountWithoutId): Result<AccountDetailed>

    /**
     * Fetches a single account by ID
     *
     * @param id account id
     *
     * @return Result<[MainAccount]>
     */
    suspend fun fetchAccount(id: String): Result<MainAccount>

    /**
     * Updates an existing account
     *
     * @param id account id
     * @param body [AccountWithoutId]
     *
     * @return Result<[AccountDetailed]>
     */
    suspend fun updateAccount(
        id: String,
        body: AccountWithoutId
    ): Result<AccountDetailed>

    /**
     * Deletes an account by ID
     *
     * @param id account id
     *
     * @return Result<[Boolean]> if account was removed then returns true, else false
     */
    suspend fun deleteAccount(id: String): Result<Unit>

    /**
     * Fetches account transaction history
     *
     * @param id account id
     *
     * @return Result<[AccountHistory]>
     */
    suspend fun fetchAccountHistory(id: String): Result<AccountHistory>
}
