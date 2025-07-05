package com.yandex.finance.core.data.observer

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

interface NetworkConnectivityObserver {

    val isConnected: Flow<Boolean>
}

class NetworkConnectivityObserverImpl(context: Context) : NetworkConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager

    override val isConnected: Flow<Boolean>
        get() = callbackFlow {
            Timber.d("isConnected was called")

            val currentState = getCurrentNetworkState()
            trySend(currentState)

            val callback = networkCallback()

            connectivityManager.registerDefaultNetworkCallback(callback)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }

    private fun ProducerScope<Boolean>.networkCallback(): NetworkCallback =
        object : NetworkCallback() {

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                Timber.d("onCapabilitiesChanged was called")
                super.onCapabilitiesChanged(network, networkCapabilities)

                val connected = networkCapabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_VALIDATED
                )

                trySend(connected)
                Timber.d("onCapabilitiesChanged reached to the end. status: $connected")
            }

            override fun onUnavailable() {
                Timber.d("onUnavailable was called. status: false")
                super.onUnavailable()

                trySend(false)
            }

            override fun onLost(network: Network) {
                Timber.d("onLost was called. status: false")
                super.onLost(network)

                trySend(false)
            }
        }

    private fun getCurrentNetworkState(): Boolean {
        return try {
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true
        } catch (e: Exception) {
            false
        }
    }
}
