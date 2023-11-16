package com.project.foodtracker.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun FoodTrackerBottomNavBar(goHome: (() -> Unit)?, goDiscover: (() -> Unit)?, modifier: Modifier) {


    BottomAppBar(

        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        actions = {
            if (goHome != null) {
                IconButton(onClick = goHome) {
                    Icon(Icons.Filled.Home, contentDescription = "navigate to home screen")
                }
            }

            if (goDiscover != null) {
                IconButton(onClick = goDiscover) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "navigate to about page",
                    )
                }
            }
        },
    )



    NavigationBar() {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Rounded.Face,
                    contentDescription = "Favorites",
                    tint = Color(0xff49454f)
                )
            },
            label = {
                Text(
                    text = "Favorites",
                    color = Color(0xff49454f),
                    textAlign = TextAlign.Center,
                    lineHeight = 1.33.em,
                    style = MaterialTheme.typography.labelMedium
                )
            },
            alwaysShowLabel = true,
            selected = false,
            onClick = { },
            modifier = Modifier
                .weight(weight = 0.33f)
        )
        NavigationBarItem(
            alwaysShowLabel = true,
            selected = false,
            onClick = { goHome },
 
            modifier = Modifier.weight(weight = 0.33f),
            icon = {

                Icon(
                    Icons.Rounded.Home,
                    contentDescription = "Home",
                    tint = Color(0xff49454f)
                )
            },
            label = {
                Text(
                    text = "Home",
                    color = Color(0xff49454f),
                    textAlign = TextAlign.Center,
                    lineHeight = 1.33.em,
                    style = MaterialTheme.typography.labelMedium
                )
            },

        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Rounded.Search,
                    contentDescription = "Discover",
                    tint = Color(0xff49454f)
                )
            },
            label = {
                Text(
                    text = "Discover",
                    color = Color(0xff49454f),
                    textAlign = TextAlign.Center,
                    lineHeight = 1.33.em,
                    style = MaterialTheme.typography.labelMedium
                )
            },
            alwaysShowLabel = true,
            selected = false,
            onClick = { goDiscover },
            modifier = Modifier
                .weight(weight = 0.33f)
        )
    }

}

@Composable
@Preview
fun FoodTrackerBottomBarPreview() {
    FoodTrackerBottomNavBar(null, null, Modifier)
}
