package com.yandex.finance.core.network.category.service

import com.yandex.finance.core.network.category.model.NetworkCategory
import com.yandex.finance.core.network.safeResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class KtorCategoryService(
    private val client: HttpClient,
    private val dispatcher: CoroutineDispatcher,
) : CategoryService {

    override suspend fun fetchCategories(): Result<List<NetworkCategory>> {
        Timber.d("fetchCategories was called")

        return withContext(dispatcher) {
            client.safeResponse<List<NetworkCategory>> {
                get {
                    url {
                        protocol = URLProtocol.HTTP
                        path("categories")
                    }
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }

    override suspend fun fetchCategoriesByType(isIncome: Boolean): Result<List<NetworkCategory>> {
        Timber.d("fetchCategoriesByType was called. isIncome: $isIncome")

        return withContext(dispatcher) {
            client.safeResponse<List<NetworkCategory>> {
                get {
                    url {
                        protocol = URLProtocol.HTTP
                        path("categories", "type", "$isIncome")
                    }
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }
}
