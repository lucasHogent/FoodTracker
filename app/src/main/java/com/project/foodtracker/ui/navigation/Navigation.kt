package com.project.foodtracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.foodtracker.ui.discover.DiscoverScreen
import com.project.foodtracker.ui.favorites.FavoritesScreen
import com.project.foodtracker.ui.home.HomeScreen
import com.project.foodtracker.ui.product.components.ProductDetailView
import com.project.foodtracker.ui.util.Screen
 import com.project.foodtracker.ui.product_edit.components.ProductDetailEditView

@Composable
fun NavComponent(navController: NavHostController,
                 modifier: Modifier,
){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {

        composable(Screen.Discover.route) { DiscoverScreen(navController) }
        composable(Screen.Favorites.route) { FavoritesScreen(navController) }
        composable(Screen.Home.route) { HomeScreen() }
        composable(
            route = Screen.ProductDetail.route + "?productId={productId}",
            arguments = listOf(
                navArgument(name = "productId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            ProductDetailView(navController = navController, onNavigate = {
                navController.navigate(it.route)
            },)
        }
        composable(
            route = Screen.ProductDetailEdit.route + "?productId={productId}",
            arguments = listOf(
                navArgument(name = "productId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            ProductDetailEditView(navController, onNavigate = {
                navController.navigate(it.route)})
        }


    }
}