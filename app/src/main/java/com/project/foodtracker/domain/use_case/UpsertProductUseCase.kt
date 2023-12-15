package com.project.foodtracker.domain.use_case

import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.repository.IProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use case for updating product details.
 *
 * @property repository The repository for managing product data.
 */
class UpsertProductUseCase @Inject constructor(
    private val repository: IProductRepository
) {
    /**
     * Updates the details of a product in the repository.
     *
     * @param product The updated details of the product.
     */
    suspend operator fun invoke(product: ProductDetailModel) {
        withContext(Dispatchers.IO) {
            repository.updateProduct(product)
        }
    }
}