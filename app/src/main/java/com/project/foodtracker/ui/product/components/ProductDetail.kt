package com.project.foodtracker.ui.product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.ui.product.ProductDetailState

@Composable
fun ProductDetailSection(
    modifier: Modifier = Modifier,
    state: ProductDetailState
) {

    Box(modifier = Modifier.fillMaxSize()) {

        state.product?.let { product ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    ProductTitleAndScore(product = product)
                    ProductPriceAndImage(product = product)
                    Spacer(modifier = Modifier.height(15.dp))
                    Text("Instructions", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(text = product.instructions, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(15.dp))
                    Text("DishTypes", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(15.dp))
                    DishTypesRow(product = product)
                    Spacer(modifier = Modifier.height(15.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(15.dp))
                    ProductDetailsInfo(product = product)
                    Spacer(modifier = Modifier.height(15.dp))
                    Text("Occasions", style = MaterialTheme.typography.bodyLarge)

                }
                items(product.occasions) { occasion ->
                    OccasionItem(
                        occasion = occasion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Divider()
                }

            }
        }

    }
}

@Composable
private fun ProductTitleAndScore(product: ProductDetailModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = product.title,
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 24.sp),
            modifier = Modifier.weight(20f)
        )
        Text(
            text = "Score: ${product.healthScore}",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(8f)
        )
    }
}

@Composable
private fun ProductPriceAndImage(product: ProductDetailModel) {
    Row {
        Text(
            text = if (product.cheap) "cheap" else "expensive",
            color = if (product.cheap) Color.Green else Color.Red,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.End,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(2f)
        )
    }
    Spacer(modifier = Modifier.height(15.dp))
    Row {
        AsyncImage(
            model = product.image,
            contentDescription = "Product Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(4.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primary)
        )
        Divider()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DishTypesRow(product: ProductDetailModel) {
    FlowRow(modifier = Modifier.fillMaxWidth()) {
        product.dishTypes.forEach { type ->
            DishType(type = type)
        }
    }
}

@Composable
private fun ProductDetailsInfo(product: ProductDetailModel) {
    Text("Servings: ${product.servings}", style = MaterialTheme.typography.bodyMedium)
    Text("Ready in Minutes: ${product.readyInMinutes}", style = MaterialTheme.typography.bodyMedium)
    Text("Source: ${product.sourceName}", style = MaterialTheme.typography.bodyMedium)
    Text(
        "Spoonacular Source: ${product.spoonacularSourceUrl}",
        style = MaterialTheme.typography.bodyMedium
    )
    Text("License: ${product.license ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
    Text(
        "Price Per Serving: ${product.pricePerServing}",
        style = MaterialTheme.typography.bodyMedium
    )
    Text("Credits: ${product.creditsText}", style = MaterialTheme.typography.bodyMedium)
}
