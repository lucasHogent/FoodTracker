package com.project.foodtracker.ui.product_edit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductTopAppBar(onNavigate: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Edit Product Detail") },
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 15.dp)
            .fillMaxWidth()
            .statusBarsPadding(),
        navigationIcon = { EditProductAppBarIcon(onNavigate = onNavigate) }
    )
}