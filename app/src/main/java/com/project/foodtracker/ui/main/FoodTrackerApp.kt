package com.project.foodtracker.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.foodtracker.ui.discover.DiscoverScreen
import com.project.foodtracker.ui.main.components.FoodTrackerBottomNavBar
import com.project.foodtracker.ui.main.components.NavItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodTrackerApp(navController: NavHostController = rememberNavController()) {

    val goHome: () -> Unit = {
        navController.popBackStack(
            NavItems.HOME.name,
            inclusive = false,
        )
    }
    val goDiscover = { navController.navigate(NavItems.DISCOVER.name) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Top app bar")
                })
        },
        bottomBar = { FoodTrackerBottomNavBar(goHome, goDiscover, modifier = Modifier) },

        ) { innerPadding ->
        OverviewFoodTracker(modifier = Modifier.padding(innerPadding))

        NavHost(
            navController = navController,
            startDestination = NavItems.HOME.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = NavItems.HOME.name) {
                DiscoverScreen("HOME")
            }
            composable(route = NavItems.FAVORITES.name) {
                DiscoverScreen("FAVORITES")
            }
            composable(route = NavItems.DISCOVER.name) {
                DiscoverScreen("DISCOVER")
            }
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


