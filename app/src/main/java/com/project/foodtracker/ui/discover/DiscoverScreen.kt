package com.project.foodtracker.ui.discover

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.foodtracker.ui.product_list.ProductListOverview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Discover products")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 15.dp)
                    .fillMaxWidth()
                    .statusBarsPadding(),
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            ProductListOverview(
                onNavigate = {
                    navController.navigate(it.route)
                },
                searchVisible = true,
                modifier = modifier.padding(contentPadding),
                favoriteProducts = false,
            )
        }

    }
}


@Preview
@Composable
fun DiscoverScreenPreview() {
    val navController: NavController = rememberNavController()
    DiscoverScreen(navController)
}

