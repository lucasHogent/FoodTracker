package com.project.foodtracker.ui.product.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.project.foodtracker.ui.components.AddToFavoritesButton
import com.project.foodtracker.ui.product.ProductDetailEvent
import com.project.foodtracker.ui.product.ProductDetailState
import com.project.foodtracker.ui.product.ProductDetailViewModel

@Composable
fun ProductDetailFloatingActionButton(isFavorite: Boolean,
                                      viewModel: ProductDetailViewModel,
                                      state: ProductDetailState
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(8.dp), // Adjust the spacing as needed
    ) {
        AddToFavoritesButton(
            isFavorite = isFavorite,
            onClick = {
                val selectedProduct = state.product
                selectedProduct?.let {
                    if (isFavorite)
                        viewModel.onEvent(ProductDetailEvent.OnRemoveFavoriteProductClick(selectedProduct))
                    else
                        viewModel.onEvent(ProductDetailEvent.OnAddFavoriteProductClick(selectedProduct))
                }
            }
        )

        FloatingActionButton(onClick = {
            state.product?.let {
                viewModel.onEvent(ProductDetailEvent.OnClickEditProductDetail(it.productId))
            }
        }) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit"
            )
        }
    }
}