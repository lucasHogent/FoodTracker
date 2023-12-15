package com.project.foodtracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun AddToFavoritesButton(
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
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