package com.yandex.finance.core.network.account.service

import com.yandex.finance.core.network.account.model.NetworkAccountDetailed
import com.yandex.finance.core.network.account.model.NetworkAccountHistory
import com.yandex.finance.core.network.account.model.NetworkAccountWithoutId
import com.yandex.finance.core.network.account.model.NetworkMainAccount
import com.yandex.finance.core.network.safeResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class KtorAccountService(
    private val client: HttpClient,
    private val dispatcher: CoroutineDispatcher,
) : AccountService {

    override suspend fun fetchAccounts(): Result<List<NetworkAccountDetailed>> {
        Timber.d("fetchAccounts was called")

        return withContext(dispatcher) {
            client.safeResponse<List<NetworkAccountDetailed>> {
                get {
                    url {
                        protocol = URLProtocol.HTTP
                        path("accounts")
                    }
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }

    override suspend fun createAccount(body: NetworkAccountWithoutId): Result<NetworkAccountDetailed> {
        Timber.d("createAccount was called")

        return withContext(dispatcher) {
            client.safeResponse<NetworkAccountDetailed> {
                post {
                    url {
                        protocol = URLProtocol.HTTP
                        path("accounts")
                    }
                    contentType(ContentType.Application.Json)
                    setBody(body = body)
                }
            }
        }
    }

    override suspend fun fetchAccount(id: String): Result<NetworkMainAccount> {
        Timber.d("fetchAccount was called")

        return withContext(dispatcher) {
            client.safeResponse<NetworkMainAccount> {
                get {
                    url {
                        protocol = URLProtocol.HTTP
                        path("accounts", id)
                    }
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }

    override suspend fun updateAccount(
        id: String,
        body: NetworkAccountWithoutId
    ): Result<NetworkAccountDetailed> {
        Timber.d("updateAccount was called. id: $id, body: $body")

        return withContext(dispatcher) {
            client.safeResponse<NetworkAccountDetailed> {
                put {
                    url {
                        protocol = URLProtocol.HTTP
                        path("accounts", id)
                    }
                    contentType(ContentType.Application.Json)
                    setBody(body = body)
                }
            }
        }
    }

    override suspend fun deleteAccount(id: String): Result<Unit> {
        Timber.d("deleteAccount was called. id: $id")

        return withContext(dispatcher) {
            client.safeResponse<Unit> {
                delete {
                    url {
                        protocol = URLProtocol.HTTP
                        path("accounts", id)
                    }
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }

    override suspend fun fetchAccountHistory(id: String): Result<NetworkAccountHistory> {
        Timber.d("fetchAccountHistory was called. id: $id")

        return withContext(dispatcher) {
            client.safeResponse<NetworkAccountHistory> {
                get {
                    url {
                        protocol = URLProtocol.HTTP
                        path("accounts", id, "history")
                    }
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }
}
