package com.project.foodtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.project.foodtracker.ui.components.FoodTrackerBottomNavBar
import com.project.foodtracker.ui.navigation.NavComponent
import com.project.foodtracker.ui.theme.FoodTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContent {
            val navController = rememberNavController()
            FoodTrackerTheme (){
                Scaffold(
                    bottomBar = {
                        FoodTrackerBottomNavBar(navController)
                    }
                ) { innerPadding ->
                    NavComponent(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    FoodTrackerTheme {
        FoodTrackerApp()
    }
}