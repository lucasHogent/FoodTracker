package com.project.foodtracker.ui

sealed class Screen(val route: String) {
    object ProductDetail: Screen("product_detail_screen")
    object Discover: Screen("discovery_screen")
    object Favorites: Screen("favorites_screen")
}