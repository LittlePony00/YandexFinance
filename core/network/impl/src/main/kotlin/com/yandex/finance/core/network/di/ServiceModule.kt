package com.yandex.finance.core.network.di

import com.yandex.finance.core.common.DoDispatchers
import com.yandex.finance.core.network.account.service.AccountService
import com.yandex.finance.core.network.account.service.KtorAccountService
import com.yandex.finance.core.network.category.service.CategoryService
import com.yandex.finance.core.network.category.service.KtorCategoryService
import com.yandex.finance.core.network.transaction.service.KtorTransactionService
import com.yandex.finance.core.network.transaction.service.TransactionService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val provideNetworkServiceModule = module {

    single<AccountService> {
        KtorAccountService(
            client = get(),
            dispatcher = get(named(DoDispatchers.IO.name))
        )
    }

    single<CategoryService> {
        KtorCategoryService(
            client = get(),
            dispatcher = get(named(DoDispatchers.IO.name))
        )
    }

    single<TransactionService> {
        KtorTransactionService(
            client = get(),
            dispatcher = get(named(DoDispatchers.IO.name))
        )
    }
}
