package com.project.foodtracker.ui.product_edit.components

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.project.foodtracker.ui.product.ProductDetailState
import com.project.foodtracker.ui.product_edit.ProductDetailEditEvent
import com.project.foodtracker.ui.product_edit.ProductDetailEditViewModel
import com.project.foodtracker.ui.util.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScaffold(
    state: ProductDetailState,
    snackbarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProductDetailEditViewModel
) {
    Scaffold(
        topBar = { EditProductTopAppBar(onNavigate = { viewModel.onEvent(ProductDetailEditEvent.OnClickGoBack()) }) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = { EditProductFab(onClick = { viewModel.onEvent(ProductDetailEditEvent.OnClickSaveProductDetail()) }) },
        content = { EditProductContent(it, state, viewModel = viewModel) }
    )
}