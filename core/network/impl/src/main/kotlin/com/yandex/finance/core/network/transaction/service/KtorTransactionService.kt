package com.yandex.finance.core.network.transaction.service

import com.yandex.finance.core.network.safeResponse
import com.yandex.finance.core.network.transaction.model.NetworkCreatedTransaction
import com.yandex.finance.core.network.transaction.model.NetworkTransaction
import com.yandex.finance.core.network.transaction.model.NetworkTransactionWithoutId
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import timber.log.Timber

class KtorTransactionService(
    private val client: HttpClient,
    private val dispatcher: CoroutineDispatcher,
) : TransactionService {

    override suspend fun createTransaction(body: NetworkTransactionWithoutId): Result<NetworkCreatedTransaction> {
        Timber.d("createTransaction was called. body: $body")
        Timber.d("CREATE TRANSACTION URL: https://shmr-finance.ru/api/v1/transactions")
        Timber.d("CREATE TRANSACTION BODY JSON: ${Json.encodeToString(NetworkTransactionWithoutId.serializer(), body)}")

        return withContext(dispatcher) {
            client.safeResponse<NetworkCreatedTransaction> {
                post {
                    url {
                        protocol = URLProtocol.HTTP
                        path("transactions")
                    }
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
            }
        }
    }

    override suspend fun fetchTransactionById(id: Int): Result<NetworkTransaction> {
        Timber.d("fetchTransactionById was called. id: $id")

        return withContext(dispatcher) {
            client.safeResponse<NetworkTransaction> {
                get {
                    url {
                        protocol = URLProtocol.HTTP
                        path("transactions", "$id")
                    }
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }

    override suspend fun updateTransaction(
        id: Int,
        body: NetworkTransactionWithoutId
    ): Result<NetworkTransaction> {
        Timber.d("updateTransaction was called. id: $id, body: $body")
        Timber.d("UPDATE TRANSACTION URL: https://shmr-finance.ru/api/v1/transactions/$id")
        Timber.d("UPDATE TRANSACTION BODY JSON: ${Json.encodeToString(NetworkTransactionWithoutId.serializer(), body)}")

        return withContext(dispatcher) {
            client.safeResponse<NetworkTransaction> {
                put {
                    url {
                        protocol = URLProtocol.HTTP
                        path("transactions", "$id")
                    }
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
            }
        }
    }

    override suspend fun deleteTransaction(id: Int): Result<Unit> {
        Timber.d("deleteTransaction was called. id: $id")

        return withContext(dispatcher) {
            client.safeResponse<Unit> {
                delete {
                    url {
                        protocol = URLProtocol.HTTP
                        path("transactions", "$id")
                    }
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }

    override suspend fun fetchTransactionsByPeriod(
        id: String,
        startDate: String?,
        endDate: String?
    ): Result<List<NetworkTransaction>> {
        Timber.d("fetchTransactionsByPeriod was called. id: $id, startDate: $startDate, endDate: $endDate")

        return withContext(dispatcher) {
            client.safeResponse<List<NetworkTransaction>> {
                get {
                    url {
                        protocol = URLProtocol.HTTP
                        path("transactions", "account", id, "period")
                    }
                    contentType(ContentType.Application.Json)
                    parameter(key = "startDate", value = startDate)
                    parameter(key = "endDate", value = endDate)
                }
            }
        }
    }
}
