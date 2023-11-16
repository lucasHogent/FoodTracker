package com.project.foodtracker.ui.main.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.project.foodtracker.R

enum class NavItems(@StringRes val title: Int) {
    FAVORITES(title = R.string.favorites),
    HOME(title = R.string.app_name),
    DISCOVER(title = R.string.discover),
}
