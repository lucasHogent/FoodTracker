package com.project.foodtracker.ui.product.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddToFavoritesButton(
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .size(56.dp)
    ) {
        if (isFavorite)
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Add to Favorites"
            )
        else
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "remove from Favorites"
            )
    }
}