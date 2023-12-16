package com.project.foodtracker.ui.product_edit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.foodtracker.ui.product.ProductDetailState
import com.project.foodtracker.ui.product_edit.ProductDetailEditEvent
import com.project.foodtracker.ui.product_edit.ProductDetailEditViewModel

@Composable
fun EditProductSection(state: ProductDetailState, viewModel: ProductDetailEditViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        state.product?.let { editProduct ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    EditProductTextField(
                        label = "Title",
                        value = editProduct.title,
                        onValueChange = {
                            viewModel.onEvent(
                                ProductDetailEditEvent.OnProductTitleChanged(
                                    it
                                )
                            )
                        }
                    )

                    EditProductTextField(
                        label = "Image URL",
                        value = editProduct.image,
                        onValueChange = {
                            viewModel.onEvent(
                                ProductDetailEditEvent.OnProductImageChanged(
                                    it
                                )
                            )
                        }
                    )

                    EditProductTextField(
                        label = "Servings",
                        value = editProduct.servings.toString(),
                        onValueChange = {
                            viewModel.onEvent(
                                ProductDetailEditEvent.OnProductServingsChanged(
                                    it.toIntOrNull() ?: 0
                                )
                            )
                        }
                    )

                    EditProductTextField(
                        label = "Ready in Minutes",
                        value = editProduct.readyInMinutes.toString(),
                        onValueChange = {
                            viewModel.onEvent(
                                ProductDetailEditEvent.OnProductReadyInMinutesChanged(
                                    it.toIntOrNull() ?: 0
                                )
                            )
                        }
                    )

                    EditProductTextField(
                        label = "Health Score",
                        value = editProduct.healthScore.toString(),
                        onValueChange = {
                            viewModel.onEvent(
                                ProductDetailEditEvent.OnProductHealthScoreChanged(
                                    it.toFloatOrNull() ?: 0f
                                )
                            )
                        }
                    )

                    EditProductTextField(
                        label = "Spoonacular Score",
                        value = editProduct.spoonacularScore.toString(),
                        onValueChange = {
                            viewModel.onEvent(
                                ProductDetailEditEvent.OnProductSpoonacularScoreChanged(
                                    it.toFloatOrNull() ?: 0f
                                )
                            )
                        }
                    )

                    EditProductTextField(
                        label = "Price Per Serving",
                        value = editProduct.pricePerServing.toString(),
                        onValueChange = {
                            if (it.isNotEmpty())
                                viewModel.onEvent(
                                    ProductDetailEditEvent.OnProductPricePerServingChanged(
                                        it.toFloatOrNull() ?: 0f
                                    )
                                )
                        }
                    )

                    Row(
                        modifier = Modifier.padding(5.dp),
                    ) {
                        Text("Cheap ?")
                        Checkbox(
                            checked = editProduct.cheap,
                            onCheckedChange = {
                                viewModel.onEvent(
                                    ProductDetailEditEvent.OnProductCheapChanged(
                                        it
                                    )
                                )
                            }

                        )
                    }

                    EditProductTextField(
                        label = "Credits Text",
                        value = editProduct.creditsText,
                        onValueChange = {
                            viewModel.onEvent(
                                ProductDetailEditEvent.OnProductCreditsTextChanged(
                                    it
                                )
                            )
                        }
                    )

                }
            }
        }
    }
}
