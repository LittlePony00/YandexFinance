package com.yandex.finance.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import timber.log.Timber

private const val MAX_RETRIES = 3
private const val RETRY_DELAY_MS = 2000L

internal suspend inline fun <reified T> HttpResponse.handleResponse(): T {
    Timber.d("HTTP RESPONSE: Status=${status.value}, URL=${call.request.url}")
    return when {
        status.value == 500 -> {
            val methodNameForLogging = methodName ?: "Network call"
            val errorBody = bodyAsText()
            Timber.e("$methodNameForLogging failed with status ${status.value}, error: $errorBody")
            throw IllegalStateException("HTTP ${status.value}: $errorBody")
        }

        status.value in 200..299 -> {
            body<T>()
        }

        else -> {
            val methodNameForLogging = methodName ?: "Network call"
            val errorBody = bodyAsText()
            Timber.e("$methodNameForLogging failed with status ${status.value}, error: $errorBody")
            throw IllegalStateException("HTTP ${status.value}: $errorBody")
        }
    }
}

internal suspend inline fun <reified T> HttpClient.safeResponse(
    block: HttpClient.() -> HttpResponse
): Result<T> =
    runCatching {
        var lastException: Throwable? = null
        var attempt = 0

        while (attempt < MAX_RETRIES) {
            try {
                val response = block()
                
                if (response.status.value == 500) {
                    val methodNameForLogging = methodName ?: "Network call"
                    Timber.w("$methodNameForLogging failed with status ${response.status.value}, attempt ${attempt + 1}/$MAX_RETRIES")
                    
                    if (attempt < MAX_RETRIES - 1) {
                        delay(RETRY_DELAY_MS)
                        attempt++
                        continue
                    } else {
                        return@runCatching response.handleResponse<T>()
                    }
                }
                
                return@runCatching response.handleResponse<T>()
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                
                lastException = e
                val methodNameForLogging = methodName ?: "Network call"
                Timber.w("Retry attempt ${attempt + 1} failed: ${e.message}")
                
                if (attempt < MAX_RETRIES - 1) {
                    delay(RETRY_DELAY_MS)
                    attempt++
                    continue
                } else {
                    throw e
                }
            }
        }
        
        throw lastException ?: IllegalStateException("Request failed after $MAX_RETRIES retries")
    }.onSuccess {
        methodName?.run { Timber.d("$this method was called with success") } ?: run {
            Timber.d("Network call was called with success")
        }
    }.onFailure { throwable ->
        methodName?.run {
            Timber.w(
                "Method '$this' failed with exception: ${throwable.javaClass.simpleName}" +
                        ", with message: ${throwable.message}"
            )
        } ?: run {
            Timber.d(
                "Network call failed with exception: ${throwable.javaClass.simpleName}" +
                        ", with message: ${throwable.message}"
            )
        }
    }

private val methodName: String?
    inline get() = Throwable().stackTrace
        .firstOrNull()
        ?.run { Regex(pattern = "(\\$\\d+)+$").replace(className, "") }
        ?.substringAfterLast('$')
