package com.project.foodtracker.ui.discover

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DiscoverScreen(text: String) {
   Card (modifier = Modifier.padding(2.dp)) {
       Text(text = text)
   }
}
