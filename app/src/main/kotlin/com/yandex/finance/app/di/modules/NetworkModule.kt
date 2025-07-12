package com.yandex.finance.app.di.modules

import com.yandex.finance.core.network.account.service.AccountService
import com.yandex.finance.core.network.account.service.KtorAccountService
import com.yandex.finance.core.network.category.service.CategoryService
import com.yandex.finance.core.network.category.service.KtorCategoryService
import com.yandex.finance.core.network.impl.BuildConfig
import com.yandex.finance.core.network.transaction.service.KtorTransactionService
import com.yandex.finance.core.network.transaction.service.TransactionService
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
    }
    
    @Provides
    @Singleton
    fun provideHttpClient(json: Json): HttpClient {
        return HttpClient(Android) {
            engine {
                connectTimeout = 5000
                socketTimeout = 5000
            }

            install(ContentNegotiation) {
                json(json)
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 5000
                connectTimeoutMillis = 5000
                socketTimeoutMillis = 5000
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d("HTTP_CLIENT: $message")
                    }
                }
                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                header(HttpHeaders.Authorization, "Bearer ${BuildConfig.API_TOKEN}")
                url(BuildConfig.BASE_URL)
            }
        }
    }
    
    @Provides
    @Singleton
    @Named("IO")
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
    
    @Provides
    @Singleton
    @Named("DEFAULT")
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
    
    @Provides
    @Singleton
    fun provideAccountService(
        client: HttpClient,
        @Named("IO") dispatcher: CoroutineDispatcher
    ): AccountService {
        return KtorAccountService(client, dispatcher)
    }
    
    @Provides
    @Singleton
    fun provideCategoryService(
        client: HttpClient,
        @Named("IO") dispatcher: CoroutineDispatcher
    ): CategoryService {
        return KtorCategoryService(client, dispatcher)
    }
    
    @Provides
    @Singleton
    fun provideTransactionService(
        client: HttpClient,
        @Named("IO") dispatcher: CoroutineDispatcher
    ): TransactionService {
        return KtorTransactionService(client, dispatcher)
    }
} 