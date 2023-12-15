package com.project.foodtracker.ui.util

sealed class Screen(val route: String) {
    object ProductDetail: Screen("product_detail_screen")
    object ProductDetailEdit: Screen("product_detail_edit_screen")
    object Discover: Screen("discovery_screen")
    object Favorites: Screen("favorites_screen")
    object Home: Screen("home_screen")
}