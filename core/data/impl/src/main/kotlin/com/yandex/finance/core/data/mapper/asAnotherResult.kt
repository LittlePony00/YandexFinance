package com.yandex.finance.core.data.mapper

inline fun <T, R> Result<T>.asAnotherResult(transform: (T) -> R): Result<R> {
    return fold(
        onSuccess = { Result.success(transform(it)) },
        onFailure = { Result.failure(it) }
    )
}