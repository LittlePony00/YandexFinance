package com.yandex.finance.core.data.mapper

inline fun <T, R> Result<T>.asExternalResult(transform: (T) -> R): Result<R> {
    return fold(
        onSuccess = { response -> Result.success(transform(response)) },
        onFailure = { exception -> Result.failure(exception) }
    )
}
