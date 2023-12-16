package com.project.foodtracker.ui.product.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.foodtracker.ui.product.ProductDetailViewModel
import com.project.foodtracker.ui.util.UiEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailView(
    navController: NavController,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.productState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val isFavorite by viewModel.isFavoriteProduct.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.PopBackStack -> navController.popBackStack()
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                    )

                }
                is UiEvent.Navigate -> onNavigate(event)
            }
        }
    }

    Scaffold(
        topBar = {
            ProductDetailTopAppBar(navController)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {
            ProductDetailContent(modifier = Modifier.padding(it), state)
        },
        floatingActionButton = {
            ProductDetailFloatingActionButton(isFavorite, viewModel, state)
        },
    )
}










