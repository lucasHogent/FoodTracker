package com.project.foodtracker.ui.product


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.foodtracker.ui.product.components.AddToFavoritesButton
import com.project.foodtracker.ui.product.components.DishType
import com.project.foodtracker.ui.product.components.OccasionItem
import kotlinx.coroutines.launch
import timber.log.Timber


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

    LaunchedEffect(isFavorite) {

        Timber.d("isFavorite changed: $isFavorite")

    }

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
                    if (selectedProduct != null) {
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
                        AsyncImage(
                            model = product.image,
                            contentDescription = "Product Image",
                            contentScale = ContentScale.Crop, // Adjust contentScale as needed
                            modifier = Modifier
                                .size(100.dp)
                                .clip(shape = CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                        Text(
                            text = "${product.productId}. ${product.title} Score: ${product.healthScore}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(8f)
                        )
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
                    Text(
                        text = product.instructions,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "DishTypes",
                        style = MaterialTheme.typography.bodySmall
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
                    Text(
                        text = "Occasions",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(15.dp))
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
