package com.yandex.finance.core.data.observer

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
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

            val callback = object : NetworkCallback() {

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
                }

                override fun onUnavailable() {
                    Timber.d("onUnavailable was called")
                    super.onUnavailable()

                    trySend(false)
                }

                override fun onLost(network: Network) {
                    Timber.d("onLost was called")
                    super.onLost(network)

                    trySend(false)
                }

                override fun onAvailable(network: Network) {
                    Timber.d("onAvailable was called")
                    super.onAvailable(network)

                    trySend(true)
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()

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
