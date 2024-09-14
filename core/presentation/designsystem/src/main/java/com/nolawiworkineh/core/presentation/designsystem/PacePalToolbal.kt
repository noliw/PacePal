@file:OptIn(ExperimentalMaterial3Api::class)

package com.nolawiworkineh.core.presentation.designsystem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nolawiworkineh.core.presentation.designsystem.components.util.DropdownMenuItem

@Composable
fun PacePalToolbar(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    title: String = "",
    menuItems: List<DropdownMenuItem> = emptyList(),
    onMenuItemClick: (DropdownMenuItem) -> Unit = {},
    onBackClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    startContent: @Composable () -> Unit = {},
) {

}

