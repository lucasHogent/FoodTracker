package com.project.foodtracker.ui.product_edit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.foodtracker.ui.util.UiEvent
import timber.log.Timber

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailEditScreen(
    navController: NavController,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProductDetailEditViewModel = hiltViewModel()
) {

    val state by viewModel.productState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {

                is UiEvent.ShowSnackbar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(ProductDetailEditEvent.OnClickUndoProductDetail())
                    }

                }

                is UiEvent.Navigate -> {
                    Timber.i("back to %s", event.toString())
                    navController.popBackStack(route = event.route, inclusive =  true, saveState = false)

                }

                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edit Product Detail")
                },
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 15.dp)
                    .fillMaxWidth()
                    .statusBarsPadding(),
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onEvent(ProductDetailEditEvent.OnClickGoBack())
                    })
                    {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

                )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(ProductDetailEditEvent.OnClickSaveProductDetail()) },
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Update"
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    Box(modifier = Modifier.fillMaxSize()) {
                        state.product?.let { editProduct ->
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(20.dp)
                            ) {
                                item {


                                    TextField(
                                        modifier = Modifier.padding(5.dp),
                                        value = editProduct.title,
                                        onValueChange = {
                                            viewModel.onEvent(
                                                ProductDetailEditEvent.OnProductTitleChanged(
                                                    it
                                                )
                                            )
                                        },
                                        label = { Text("Title") }
                                    )

                                    TextField(
                                        modifier = Modifier.padding(5.dp),
                                        value = editProduct.image,
                                        onValueChange = {
                                            viewModel.onEvent(
                                                ProductDetailEditEvent.OnProductImageChanged(
                                                    it
                                                )
                                            )
                                        },
                                        label = { Text("Image URL") }
                                    )

                                    TextField(
                                        modifier = Modifier.padding(5.dp),
                                        value = editProduct.servings.toString(),
                                        onValueChange = {
                                            viewModel.onEvent(
                                                ProductDetailEditEvent.OnProductServingsChanged(
                                                    it.toIntOrNull() ?: 0
                                                )
                                            )
                                        },
                                        label = { Text("Servings") }
                                    )

                                    TextField(
                                        modifier = Modifier.padding(5.dp),
                                        value = editProduct.readyInMinutes.toString(),
                                        onValueChange = {
                                            viewModel.onEvent(
                                                ProductDetailEditEvent.OnProductReadyInMinutesChanged(
                                                    it.toIntOrNull() ?: 0
                                                )
                                            )
                                        },
                                        label = { Text("Ready in Minutes") }
                                    )

                                    TextField(
                                        modifier = Modifier.padding(5.dp),
                                        value = editProduct.healthScore.toString(),
                                        onValueChange = {
                                            viewModel.onEvent(
                                                ProductDetailEditEvent.OnProductHealthScoreChanged(
                                                    it.toFloatOrNull() ?: 0f
                                                )
                                            )
                                        },
                                        label = { Text("Health Score") }
                                    )

                                    TextField(
                                        modifier = Modifier.padding(5.dp),
                                        value = editProduct.spoonacularScore.toString(),
                                        onValueChange = {
                                            viewModel.onEvent(
                                                ProductDetailEditEvent.OnProductSpoonacularScoreChanged(
                                                    it.toFloatOrNull() ?: 0f
                                                )
                                            )
                                        },
                                        label = { Text("Spoonacular Score") }
                                    )

                                    TextField(
                                        modifier = Modifier.padding(5.dp),
                                        value = editProduct.pricePerServing.toString(),
                                        onValueChange = {
                                            if (it.isNotEmpty())
                                                viewModel.onEvent(
                                                    ProductDetailEditEvent.OnProductPricePerServingChanged(
                                                        it.toFloatOrNull() ?: 0f
                                                    )
                                                )
                                        },
                                        label = { Text("Price Per Serving") }
                                    )

                                    Row(
                                        modifier = Modifier.padding(5.dp),
                                    ) {
                                        Text("Cheap ?")
                                        Checkbox(
                                            checked = editProduct.cheap,
                                            onCheckedChange = {
                                                viewModel.onEvent(
                                                    ProductDetailEditEvent.OnProductCheapChanged(
                                                        it
                                                    )
                                                )
                                            },

                                            )
                                    }

                                    TextField(
                                        modifier = Modifier.padding(5.dp),
                                        value = editProduct.creditsText,
                                        onValueChange = {
                                            viewModel.onEvent(
                                                ProductDetailEditEvent.OnProductCreditsTextChanged(
                                                    it
                                                )
                                            )
                                        },
                                        label = { Text("Credits Text") }
                                    )
                                }
                            }

                        }
                    }
                }
            }


        }
    )

}

