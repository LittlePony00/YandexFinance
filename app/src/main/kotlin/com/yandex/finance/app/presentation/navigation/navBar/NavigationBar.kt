package com.yandex.finance.app.presentation.navigation.navBar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.yandex.finance.core.ui.navigation.Graph
import com.yandex.finance.core.ui.theme.RobotoLabelMediumStyle
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.runtime.rememberCoroutineScope
import com.yandex.finance.core.datastore.SettingsDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun YandexFinanceNavigationBar(
    onNavBarItemClick: (Graph) -> Unit,
    navigationBackStackEntry: NavBackStackEntry?,
    navigationBarItems: List<NavigationBarItem<Graph>>,
    modifier: Modifier = Modifier,
) {
    val currentDestination = navigationBackStackEntry?.destination
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current.applicationContext
    val scope = rememberCoroutineScope()

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
                        // Haptics integration
                        scope.launch {
                            val enabled = SettingsDataStore.isHapticsEnabled(context).first()
                            if (enabled) {
                                val effect = SettingsDataStore.hapticsEffect(context).first() ?: "Click"
                                when (effect) {
                                    "Click" -> haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    "Double Click" -> {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    }
                                    "Heavy" -> haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    "Light" -> haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                }
                            }
                        }
                        onNavBarItemClick(item.route)
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
