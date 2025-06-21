package com.yandex.finance.core.ui.component.navBar

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yandex.finance.core.navigation.outcome.OutcomeFlow
import com.yandex.finance.core.ui.theme.RobotoLabelMediumStyle

private val navigationBarItems = listOf(
    NavigationBarItem.OutcomeToday,
    NavigationBarItem.IncomeToday,
    NavigationBarItem.Account,
    NavigationBarItem.Articles,
    NavigationBarItem.Settings,
)

@Composable
fun YandexFinanceNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val navigationBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navigationBackStackEntry?.destination

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        navigationBarItems.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(item.route::class)
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(OutcomeFlow.OutcomeToday) {
                                inclusive = false
                                saveState = false
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = item.icon,
                        contentDescription = stringResource(item.title),
                        tint = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.title),
                        style = RobotoLabelMediumStyle.copy(
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant
                            },
                            fontWeight = if (isSelected) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Medium
                            }
                        ),
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

fun navigationBarEnterTransition(
    targetState: NavBackStackEntry,
    initialState: NavBackStackEntry,
): EnterTransition {
    val targetStateHierarchy = targetState.destination.hierarchy
    val initialStateHierarchy = initialState.destination.hierarchy

    val fromIndex = navigationBarItems.indexOfFirst { navBarItem ->
        initialStateHierarchy.any { hierarchy -> hierarchy.hasRoute(navBarItem.route::class) }
    }
    val toIndex = navigationBarItems.indexOfFirst { navBarItem ->
        targetStateHierarchy.any { hierarchy -> hierarchy.hasRoute(navBarItem.route::class) }
    }

    if (fromIndex == -1 || toIndex == -1) {
        return fadeIn(animationSpec = tween(0))
    }

    return if (fromIndex > toIndex) {
        slideInHorizontally(initialOffsetX = { width -> -width }) + fadeIn()
    } else {
        slideInHorizontally(initialOffsetX = { width -> width }) + fadeIn()
    }
}

fun navigationBarExitTransition(
    targetState: NavBackStackEntry,
    initialState: NavBackStackEntry,
): ExitTransition {
    val targetStateHierarchy = targetState.destination.hierarchy
    val initialStateHierarchy = initialState.destination.hierarchy

    val fromIndex = navigationBarItems.indexOfFirst { navBarItem ->
        initialStateHierarchy.any { hierarchy -> hierarchy.hasRoute(navBarItem.route::class) }
    }
    val toIndex = navigationBarItems.indexOfFirst { navBarItem ->
        targetStateHierarchy.any { hierarchy -> hierarchy.hasRoute(navBarItem.route::class) }
    }

    if (fromIndex == -1 || toIndex == -1) {
        return fadeOut(animationSpec = tween(0))
    }

    return if (fromIndex > toIndex) {
        slideOutHorizontally(targetOffsetX = { width -> width }) + fadeOut()
    } else {
        slideOutHorizontally(targetOffsetX = { width -> -width }) + fadeOut()
    }
}
