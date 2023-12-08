package com.project.foodtracker.ui.product


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.foodtracker.ui.product.components.AddToFavoritesButton
import com.project.foodtracker.ui.product.components.DishType
import com.project.foodtracker.ui.product.components.OccasionItem
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {

    val state by viewModel.productState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isFavorite by viewModel.isFavoriteProduct.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Product Detail")
                },
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 15.dp)
                    .fillMaxWidth()
                    .statusBarsPadding(),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Detail"
                        )
                    }
                },

                )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                ProductDetailSection(modifier = Modifier.padding(it), state)
            }
        },
        floatingActionButton = {

            AddToFavoritesButton(
                isFavorite = isFavorite,
                onClick = {
                    val selectedProduct = viewModel.productState.value
                    selectedProduct.product?.let {
                        if (isFavorite)
                            viewModel.removeFromFavorites(it.productId)
                        else
                            viewModel.addToFavorites(it.productId)
                        scope.launch {
                            if(!isFavorite)
                            snackbarHostState.showSnackbar(
                                "Added ${it.title} to favorites"
                            )
                            else
                                snackbarHostState.showSnackbar(
                                    "Removed ${it.title} from favorites"
                                )

                        }
                    }
                },

                )
        },
    )

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductDetailSection(
    modifier: Modifier = Modifier,
    state: ProductDetailState
) {

    Box(modifier = Modifier.fillMaxSize()) {
        state.product?.let { product ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = product.title,
                            style =  MaterialTheme.typography.labelLarge.copy(fontSize = 24.sp),
                            modifier = Modifier.weight(20f)
                        )
                        Text(
                            text = "Score: ${product.healthScore}",
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(8f)
                        )
                    }
                    Row(){

                        Text(
                            text = if (product.cheap) "cheap" else "expensive",
                            color = if (product.cheap) Color.Green else Color.Red,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .align(CenterVertically)
                                .weight(2f)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(){

                        AsyncImage(
                            model = product.image,
                            contentDescription = "Product Image",
                            contentScale = ContentScale.Crop, // Adjust contentScale as needed
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.primary)
                        )

                        Divider()
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = product.instructions,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "DishTypes",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    FlowRow(

                        modifier = Modifier.fillMaxWidth()
                    ) {
                        product.dishTypes.forEach { type ->
                            DishType(type = type)
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Servings: ${product.servings}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Ready in Minutes: ${product.readyInMinutes}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Source: ${product.sourceName}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Spoonacular Source: ${product.spoonacularSourceUrl}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "License: ${product.license ?: "N/A"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Price Per Serving: ${product.pricePerServing}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Credits: ${product.creditsText}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Occasions",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                items(product.occasions) { occasion ->
                    OccasionItem(
                        occasion = occasion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Divider()
                }

            }
        }

    }
}
