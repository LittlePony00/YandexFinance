package com.yandex.finance.core.dagger

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * MapKey annotation for binding ViewModel implementations to their classes
 * Used with Dagger's @IntoMap for creating ViewModel maps
 */
@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)
