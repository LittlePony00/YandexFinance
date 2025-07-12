package com.yandex.finance.core.ui.provider

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModelProvider

/**
 * CompositionLocal for providing ViewModelFactory to composables
 */
val LocalViewModelFactory = staticCompositionLocalOf<ViewModelProvider.Factory> {
    error("ViewModelFactory not provided")
}
