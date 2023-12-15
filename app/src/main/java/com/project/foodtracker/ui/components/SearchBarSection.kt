package com.project.foodtracker.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSection (modifier: Modifier = Modifier,
                      searchText: String,
                      onSearchNameChange: (String) -> Unit,
                      onSearchClick: () -> Unit,
                      onClearInput: () -> Unit){

    var searchName by remember { mutableStateOf(searchText) }

    TextField(
        value = searchName,
        onValueChange = {
            searchName = it
            onSearchNameChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = {
            Text(text = "Search Products")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            if (searchText.isNotBlank()) {
                IconButton(onClick = onClearInput) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "clear_icon"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick()
                }
            )

    )

}