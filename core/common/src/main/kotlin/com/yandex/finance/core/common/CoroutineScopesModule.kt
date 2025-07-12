//package com.yandex.finance.core.common
//
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.SupervisorJob
//import org.koin.core.qualifier.named
//import org.koin.dsl.module
//
//val provideCoroutineScopesModule = module {
//
//    factory<CoroutineScope>(named(DoDispatchers.DEFAULT.name)) {
//        val coroutineDispatcher = get<CoroutineDispatcher>(named(DoDispatchers.DEFAULT.name))
//        CoroutineScope(SupervisorJob() + coroutineDispatcher)
//    }
//
//    factory<CoroutineScope>(named(DoDispatchers.IO.name)) {
//        val coroutineDispatcher = get<CoroutineDispatcher>(named(DoDispatchers.IO.name))
//        CoroutineScope(SupervisorJob() + coroutineDispatcher)
//    }
//}
