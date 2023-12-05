package com.project.foodtracker.ui

sealed class Screen(val route: String) {
    object ProductList: Screen("product_list_screen")
    object ProductDetail: Screen("product_detail_screen")
    object Discover: Screen("discovery_screen")
}