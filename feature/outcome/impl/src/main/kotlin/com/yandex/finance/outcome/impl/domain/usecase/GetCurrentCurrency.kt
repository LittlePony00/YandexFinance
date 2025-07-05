package com.yandex.finance.outcome.impl.domain.usecase

import com.yandex.finance.core.common.Id
import com.yandex.finance.core.data.repository.AccountRepository
import com.yandex.finance.core.domain.model.CurrencyType
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiCurrentCurrencyUseCase
import timber.log.Timber


class GetUiCurrentCurrencyUseCaseImpl(
    private val id: Id,
    private val accountRepository: AccountRepository
) : GetUiCurrentCurrencyUseCase {

    override suspend fun invoke(): CurrencyType {
        val result = accountRepository.fetchAccount(id)
        Timber.d("result: $result")

        return result.fold(
            onSuccess = {
                CurrencyType.convertFromString(it.currency)
            }, onFailure = {
                CurrencyType.RUB
            }
        )
    }
}
