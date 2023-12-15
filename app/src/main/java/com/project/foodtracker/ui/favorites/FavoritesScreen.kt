package com.project.foodtracker.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.foodtracker.ui.product_list.ProductListOverview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen (navController: NavController,
                     modifier: Modifier = Modifier,) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Favorite products")
                },
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 15.dp)
                    .fillMaxWidth()
                    .statusBarsPadding(),

            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                })
                {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            )
        }

    ) { contentPadding ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)){
            ProductListOverview(
                onNavigate = {
                    navController.navigate(it.route)
                },
                searchVisible = false,
                favoriteProducts = true,
                Modifier.padding(contentPadding))
        }

    }

}
