package com.yandex.finance.app.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.data.observer.NetworkConnectivityObserver
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel(
    connectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    private var _wasDisconnected = mutableStateOf(false)
    val wasDisconnected = _wasDisconnected

    val isConnected = connectivityObserver
        .isConnected
        .onEach { Timber.d("onEach was called. isConnected: $it") }
        .onCompletion { cause -> Timber.w("onCompletion was called. cause: $cause") }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(MAX_TIMEOUT_MILLIS),
            true
        )

    fun changeWasDisconnected(state: Boolean) {
        Timber.d("changeWasDisconnected was called. state: $state")

        viewModelScope.launch {
            _wasDisconnected.value = state
        }
    }
    
    class Factory @Inject constructor(
        private val connectivityObserver: NetworkConnectivityObserver
    ) : ViewModelProvider.Factory {
        
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(connectivityObserver) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        private const val MAX_TIMEOUT_MILLIS = 5000L
    }
}
