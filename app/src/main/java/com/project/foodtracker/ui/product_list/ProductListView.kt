package com.project.foodtracker.ui.product_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.remember
import com.project.foodtracker.ui.components.SearchBarSection
import com.project.foodtracker.ui.product_list.components.ProductListItem
import com.project.foodtracker.ui.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListOverview(
    onNavigate: (UiEvent.Navigate) -> Unit,
    searchVisible: Boolean,
    favoriteProducts: Boolean,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = hiltViewModel(),
) {
    val searchName by viewModel.searchString.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(key1 = true) {

        if (favoriteProducts) {
            viewModel.getFavoriteProducts()
        } else {
            viewModel.getProducts(searchName)
        }

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(ProductsListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Scaffold(
        topBar = {
            if (searchVisible)
                SearchBarSection(
                    searchText = searchName,
                    onSearchNameChange = {
                        viewModel.onEvent(ProductsListEvent.OnSearchNameChanged(it)
                        )
                    },
                    onSearchClick = { viewModel.onEvent(ProductsListEvent.OnSearchClick) },
                    onClearInput = { viewModel.onEvent(ProductsListEvent.OnClearSearchClick) }
                )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(viewModel.state.value.products) { index, product ->
                        ProductListItem(
                            product = product,
                            onEvent = viewModel::onEvent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {viewModel.onEvent(ProductsListEvent.OnProductClick(product))}
                        )
                    }
                }

                if (viewModel.state.value.error.isNotBlank()) {
                    Text(
                        text = viewModel.state.value.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }
                if (viewModel.state.value.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (viewModel.state.value.products.isEmpty())
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

    }
}
