package com.project.foodtracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
 import com.project.foodtracker.domain.use_case.GetTotalFavoritesUseCase
import com.project.foodtracker.domain.use_case.GetTotalProductsInDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTotalFavoritesUseCase: GetTotalFavoritesUseCase,
    private val getTotalProductsInDBUseCase: GetTotalProductsInDBUseCase,
) : ViewModel() {

    private val _favoriteProductsCount = MutableStateFlow<Int>(0)
    val favoriteProductsCount: StateFlow<Int>  = _favoriteProductsCount

    private val _totalProductsCount = MutableStateFlow<Int>(0)
    val totalProductsCount: StateFlow<Int>  = _totalProductsCount


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchData()
    }
    private fun fetchData() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val totalFavoritesCount = getTotalFavoritesUseCase.invoke()
                    _favoriteProductsCount.value = totalFavoritesCount.first()

                    val totalProductsCount = getTotalProductsInDBUseCase.invoke()
                    _totalProductsCount.value = totalProductsCount.first()
                }
            } catch (e: Exception) {
                val errorMessage = e.localizedMessage ?: "An unexpected error occurred"
                _error.value = errorMessage
            }
        }
    }
}