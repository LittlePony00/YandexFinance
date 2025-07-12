package com.yandex.finance.core.dagger

import com.yandex.finance.core.common.Dependencies
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * MapKey annotation for binding Dependencies implementations to their interfaces
 * Used with Dagger's @IntoMap for creating dependency maps
 */
@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class DependenciesKey(val value: KClass<out Dependencies>)
