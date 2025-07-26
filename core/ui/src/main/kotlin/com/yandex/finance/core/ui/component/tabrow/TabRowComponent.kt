package com.yandex.finance.core.ui.component.tabrow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TabRowComponent(
    tabs: List<Pair<String, ImageVector?>>,
    contentScreens: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(2.dp)
                        .clip(shape = RoundedCornerShape(size = 16.dp))
                )
            }
        ) {
            tabs.forEachIndexed { index, tabContent ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = tabContent.first,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    icon = {
                        tabContent.second?.let {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = it,
                                contentDescription = it.name
                            )
                        }
                    }
                )
            }
        }

        contentScreens.getOrNull(selectedTabIndex)?.invoke()
    }
}
