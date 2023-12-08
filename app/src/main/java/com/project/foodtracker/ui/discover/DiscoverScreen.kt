package com.project.foodtracker.ui.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.foodtracker.ui.discover.components.SearchBarSection
import com.project.foodtracker.ui.product_list.ProductListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    navController: NavController,
    viewModel: DiscoverViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {

    val searchName by viewModel.searchString.collectAsState()

    Scaffold(
        topBar = {
            SearchBarSection(
                searchText = searchName,
                onSearchNameChange = viewModel::searchNameChanged,
                onSearchClick = {
                    viewModel.searchProduct(searchName)
                },
                onClearInput = viewModel::clearInput
            )
        }
    ) { contentPadding ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)){
            ProductListScreen(Modifier.padding(contentPadding),
                state = viewModel.productListState,
                navController)
        }

    }
}


@Preview
@Composable
fun DiscoverScreenPreview() {
    val navController: NavController = rememberNavController()
    DiscoverScreen(navController)
}

