package com.project.foodtracker.ui.product_edit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.foodtracker.ui.product.ProductDetailState
import com.project.foodtracker.ui.product_edit.ProductDetailEditViewModel

@Composable
fun EditProductContent(
    paddingValues: PaddingValues,
    state: ProductDetailState,
    viewModel: ProductDetailEditViewModel
) {
    Column(

        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            EditProductSection(state = state, viewModel = viewModel)
        }
    }
}