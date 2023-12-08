package com.project.foodtracker.ui.discover

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.foodtracker.domain.model.ProductModel
import com.project.foodtracker.ui.Screen
import com.project.foodtracker.ui.discover.components.SearchBarSection
import com.project.foodtracker.ui.product_list.ProductListState
import com.project.foodtracker.ui.product_list.components.ProductListItem

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
            ProductListSection(Modifier.padding(contentPadding),
                state = viewModel.productListState,
                navController)
        }

    }
}

@Composable
fun ProductListSection(modifier: Modifier = Modifier,
                       state: State<ProductListState>,
                       navController: NavController,  ){

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(state.value.products) { index, product ->
                ProductListItem(
                    product = product,
                    onItemClick = {
                        navController.navigate(Screen.ProductDetail.route + "/${product.id}")
                    }
                )
            }
        }

        if (state.value.error.isNotBlank()) {
            Text(
                text = state.value.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (state.value.products.isEmpty())
            Text(
                text = "No products found!",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
    }
}

@Preview
@Composable
fun DiscoverScreenPreview() {
    val navController: NavController = rememberNavController()
    DiscoverScreen(navController)
}

