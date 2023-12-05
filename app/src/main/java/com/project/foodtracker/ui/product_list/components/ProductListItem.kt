package com.project.foodtracker.ui.product_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.project.foodtracker.domain.model.ProductModel

@Composable
fun ProductListItem(
    product: ProductModel,
    onItemClick: (ProductModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(product) }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        AsyncImage(
            model = product.image,
            contentDescription = "Product Image",
            contentScale = ContentScale.Crop, // Adjust contentScale as needed
            modifier = Modifier
                .size(50.dp) // Adjust the size as needed
                .clip(shape = CircleShape) // Optionally clip to a circle shape
                .background(MaterialTheme.colorScheme.primary) // Optionally set a background color
        )

        Spacer(modifier = Modifier.width(16.dp)) // Add space between image and text

        Text(
            text = "${product.title}. ${product.imageType} ",
            //style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis
        )


    }
}