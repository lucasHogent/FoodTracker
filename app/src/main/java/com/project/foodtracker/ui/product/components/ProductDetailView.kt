package com.project.foodtracker.ui.product.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.foodtracker.ui.product.ProductDetailEvent
import com.project.foodtracker.ui.product.ProductDetailViewModel
import com.project.foodtracker.ui.product_edit.ProductDetailEditEvent
import com.project.foodtracker.ui.product_edit.components.EditProductContent
import com.project.foodtracker.ui.product_edit.components.EditProductFab
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
    var isEditMode by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> navController.popBackStack()
                is UiEvent.ShowSnackbar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(ProductDetailEditEvent.OnClickUndoProductDetail())
                    }
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
            if (isEditMode)
                EditProductContent(modifier = Modifier.padding(it), state = state, viewModel = viewModel)
            else
                ProductDetailContent(modifier = Modifier.padding(it), state)
        },
        floatingActionButton = {
            if (isEditMode)
                EditProductFab {
                    isEditMode = false
                    viewModel.onEvent(ProductDetailEditEvent.OnClickSaveProductDetail())
                }
            else
                ProductDetailFloatingActionButton(
                    isFavorite = isFavorite,
                    onRemoveFavorite = { selectedProduct ->
                        viewModel.onEvent(
                            ProductDetailEvent.OnRemoveFavoriteProductClick(
                                selectedProduct
                            )
                        )
                    },
                    onAddFavorite = { selectedProduct ->
                        viewModel.onEvent(
                            ProductDetailEvent.OnAddFavoriteProductClick(
                                selectedProduct
                            )
                        )
                    },
                    onClickEdit = {isEditMode = true},
                    state = state
                )

        },
    )
}











