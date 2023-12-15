package com.project.foodtracker.domain.use_case

 import com.project.foodtracker.domain.repository.IProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DeleteProductUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {
    suspend operator fun invoke(productId: String) {
        withContext(Dispatchers.IO) {
            productRepository.deleteProduct(productId)
        }
    }
}