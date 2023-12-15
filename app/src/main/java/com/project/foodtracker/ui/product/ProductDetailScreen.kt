package com.project.foodtracker.ui.product


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.foodtracker.ui.components.AddToFavoritesButton
import com.project.foodtracker.ui.product.components.ProductDetailSection
import com.project.foodtracker.ui.util.UiEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
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
            TopAppBar(
                title = {
                    Text(text = "Product Detail")
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
                            contentDescription = "Detail"
                        )
                    }
                },

                )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    ProductDetailSection(
                        modifier = Modifier.padding(it), state
                    )
                }
            }

        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp), // Adjust the spacing as needed
            ) {
                AddToFavoritesButton(
                    isFavorite = isFavorite,
                    onClick = {

                        val selectedProduct = viewModel.productState.value
                        selectedProduct.product?.let {
                            if (isFavorite)
                                viewModel.onEvent(ProductDetailEvent.OnRemoveFavoriteProductClick(selectedProduct.product))
                            else
                                viewModel.onEvent(ProductDetailEvent.OnAddFavoriteProductClick(selectedProduct.product))
                        }
                    }
                )

                FloatingActionButton(onClick = {
                    state.product?.let {
                        ProductDetailEvent.OnClickEditProductDetail(
                            it.productId)
                    }?.let { viewModel.onEvent(it) }
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
            }


        },
    )
}


