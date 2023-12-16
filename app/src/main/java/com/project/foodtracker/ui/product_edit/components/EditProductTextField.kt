package com.project.foodtracker.ui.product_edit.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    TextField(
        modifier = Modifier.padding(5.dp),
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) }
    )
}
