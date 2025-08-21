package com.abhi.classconnect.sync

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow

class InternetConnectivityObserver(private val connectivityManager: ConnectivityManager) {

    private val _isConnected : MutableStateFlow<Boolean> = MutableStateFlow(checkConnection(connectivityManager))
    val isConnected = _isConnected.asStateFlow()

    fun observeNetworkStatus(): Flow<Boolean> = callbackFlow {

        // Initial state
        trySend(checkConnection(connectivityManager))

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _isConnected.value = checkConnection(connectivityManager)
                trySend(checkConnection(connectivityManager))
            }

            override fun onLost(network: Network) {
                _isConnected.value = false
                trySend(false)
            }

            override fun onUnavailable() {
                _isConnected.value = false
                trySend(false)
            }
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    private fun checkConnection(connectivityManager: ConnectivityManager): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
}
