package com.yandex.finance.core.common

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment

/**
 * Base interface for all dependency interfaces
 */
interface Dependencies

/**
 * Type alias for map of dependency classes to their implementations
 */
typealias DepsMap = Map<Class<out Dependencies>, Dependencies>

/**
 * Interface for objects that can provide dependencies
 */
interface HasDependencies {
    val depsMap: DepsMap
}

/**
 * Extension function to find dependencies of specific type from Context hierarchy (for Compose)
 * Searches through: Activity -> Application
 */
inline fun <reified D : Dependencies> Context.findDependencies(): D {
    return findDependencies(D::class.java)
}

/**
 * Non-inline version for finding dependencies from Context
 */
fun <D : Dependencies> Context.findDependencies(dependencyClass: Class<D>): D {
    val dependencyIterator = generateSequence(this as Any?) { current ->
        when (current) {
            is Activity -> current.application
            else -> null
        }
    }

    for (possibleDependencyProvider in dependencyIterator) {
        if (possibleDependencyProvider is HasDependencies) {
            val dependency = possibleDependencyProvider.depsMap[dependencyClass]
            if (dependency != null) {
                @Suppress("UNCHECKED_CAST")
                return dependency as D
            }
        }
    }

    throw IllegalStateException(
        "Unable to find dependencies of type ${dependencyClass.simpleName} " +
                "in Context hierarchy. Make sure the dependency is provided " +
                "in Activity or Application."
    )
}

/**
 * Extension function to find dependencies of specific type from Fragment hierarchy
 * Searches through: Fragment -> parentFragment -> activity -> application
 */
inline fun <reified D : Dependencies> Fragment.findDependencies(): D {
    return findDependencies(D::class.java)
}

/**
 * Non-inline version for finding dependencies
 */
fun <D : Dependencies> Fragment.findDependencies(dependencyClass: Class<D>): D {
    val dependencyIterator = generateSequence(this as Any?) { current ->
        when (current) {
            is Fragment -> current.parentFragment ?: current.activity
            is Activity -> current.application
            else -> null
        }
    }

    for (possibleDependencyProvider in dependencyIterator) {
        if (possibleDependencyProvider is HasDependencies) {
            val dependency = possibleDependencyProvider.depsMap[dependencyClass]
            if (dependency != null) {
                @Suppress("UNCHECKED_CAST")
                return dependency as D
            }
        }
    }

    throw IllegalStateException(
        "Unable to find dependencies of type ${dependencyClass.simpleName} " +
                "in Fragment hierarchy. Make sure the dependency is provided " +
                "in Fragment, Activity or Application."
    )
}
