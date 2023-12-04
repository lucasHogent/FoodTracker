package com.project.foodtracker.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.foodtracker.ui.Screen
import com.project.foodtracker.ui.discover.DiscoverScreen
import com.project.foodtracker.ui.main.components.FoodTrackerBottomNavBar
import com.project.foodtracker.ui.product.ProductDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodTrackerApp(navController: NavHostController = rememberNavController()) {

    Scaffold(

        bottomBar = {
            // Bottom navigation bar
            FoodTrackerBottomNavBar(navController)
        }

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Discover.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Screen.Discover.route) { DiscoverScreen(navController) }
            composable(Screen.ProductDetail.route + "/{productId}") {
                ProductDetailScreen(navController) }

            //composable(Screen.Search.route) { SearchScreen() }
            //composable(Screen.Profile.route) { ProfileScreen() }

        }
    }


}

@Composable
private fun OverviewFoodTracker(modifier: Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "test", fontSize = 20.sp)
            // Add more content components as needed
        }
    }
}


