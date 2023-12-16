package com.project.foodtracker.ui.product_edit.components

import android.annotation.SuppressLint
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.foodtracker.ui.product_edit.ProductDetailEditEvent
import com.project.foodtracker.ui.product_edit.ProductDetailEditViewModel
import com.project.foodtracker.ui.util.UiEvent

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProductDetailEditView(
    navController: NavController,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProductDetailEditViewModel = hiltViewModel()
) {
    val state by viewModel.productState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
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

                is UiEvent.Navigate -> {
                    navController.popBackStack()
                }

                else -> {
                    Unit
                }
            }
        }
    }

    EditProductScaffold(
        state = state,
        snackbarHostState = snackbarHostState,
        onNavigate = onNavigate,
        viewModel = viewModel
    )
}





