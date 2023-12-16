package com.project.foodtracker.ui.product.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.foodtracker.ui.product.ProductDetailState

@Composable
fun ProductDetailContent(modifier: Modifier, state: ProductDetailState) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            ProductDetailSection(
                modifier = modifier,
                state = state
            )
        }
    }
}