package com.project.foodtracker.ui.product_edit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun EditProductFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Update"
        )
    }
}
