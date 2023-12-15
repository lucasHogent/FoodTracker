package com.project.foodtracker.ui.product_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.project.foodtracker.domain.model.ProductModel
import com.project.foodtracker.ui.product_list.ProductsListEvent

@Composable
fun ProductListItem(
    product: ProductModel,
    onEvent: (ProductsListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(2.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {


                    AsyncImage(
                        model = product.image,
                        contentDescription = "Product Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(75.dp)
                            .clip(shape = CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            IconButton(onClick = {
                onEvent(ProductsListEvent.OnDeleteProductClick(product))
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }


        }
    }
}
