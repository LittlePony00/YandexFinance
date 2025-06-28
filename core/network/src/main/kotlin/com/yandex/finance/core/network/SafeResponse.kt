package com.yandex.finance.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.delay
import timber.log.Timber

private const val MAX_RETRIES = 3
private const val RETRY_DELAY_MS = 2000L

internal suspend inline fun <reified T> HttpResponse.handleResponse(): T {
    return when {
        status.value == 500 -> {
            val methodNameForLogging = methodName ?: "Network call"
            var lastException: Throwable? = null
            var attempt = 0

            while (attempt < MAX_RETRIES) {
                try {
                    Timber.w("$methodNameForLogging failed with status ${status.value}, attempt ${attempt + 1}/$MAX_RETRIES")
                    delay(RETRY_DELAY_MS)

                    return body<T>()
                } catch (e: Exception) {
                    lastException = e
                    Timber.w("Retry attempt ${attempt + 1} failed: ${e.message}")
                }
                attempt++
            }

            Timber.e("$methodNameForLogging failed after $MAX_RETRIES retries")
            throw lastException ?: IllegalStateException("Request failed after $MAX_RETRIES retries")
        }

        else -> {
            body<T>()
        }
    }
}

internal suspend inline fun <reified T> HttpClient.safeResponse(
    block: HttpClient.() -> HttpResponse
): Result<T> =
    runCatching {
        block().handleResponse<T>()
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
