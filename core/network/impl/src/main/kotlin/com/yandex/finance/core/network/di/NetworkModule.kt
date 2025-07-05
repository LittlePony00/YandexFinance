package com.yandex.finance.core.network.di

import com.yandex.finance.core.network.impl.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val provideNetworkModule = module {

    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
    }

    single {
        HttpClient(Android) {
            engine {
                connectTimeout = 5000
                socketTimeout = 5000
            }

            install(ContentNegotiation) {
                json(get())
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 5000
                connectTimeoutMillis = 5000
                socketTimeoutMillis = 5000
            }

            install(DefaultRequest) {
                header(HttpHeaders.Authorization, "Bearer ${BuildConfig.API_TOKEN}")
                url(BuildConfig.BASE_URL)
            }
        }
    }
}
