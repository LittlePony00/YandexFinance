package com.yandex.finance.core.common

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val provideDispatcherModule = module {

    single(named(DoDispatchers.IO.name)) {
        Dispatchers.IO
    }

    single(named(DoDispatchers.DEFAULT.name)) {
        Dispatchers.Default
    }
}

enum class DoDispatchers {
    IO,
    DEFAULT
}
