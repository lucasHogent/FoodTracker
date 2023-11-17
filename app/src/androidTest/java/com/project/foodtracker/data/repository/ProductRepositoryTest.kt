package com.project.foodtracker.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.project.foodtracker.data.database.dao.IProductDao
import com.project.foodtracker.data.database.entities.asDomainModel
import com.project.foodtracker.data.mock.MockProductEntityProvider
import com.project.foodtracker.domain.model.ProductModel
import com.project.foodtracker.domain.repository.IProductRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.util.UUID

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ProductRepositoryTest {

    private lateinit var productRepository: IProductRepository
    private lateinit var productDao : IProductDao

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        productDao = mock(IProductDao::class.java)
        productRepository = ProductRepository(productDao)
    }

    @Test
    fun getAllProducts_returns_productList() = testScope.runTest {

        // Given
        val mockProductList = MockProductEntityProvider.createMockProductEntityList(10)
        `when`(productDao.getAllProducts()).thenReturn(flowOf(mockProductList))

        // When
        val flowResult = productRepository.products

        // Then
        flowResult.collect { product ->
            assertEquals(mockProductList.map{ it.asDomainModel()}, product)
        }
    }

    @Test
    fun  getProductById_returns_product() = testScope.runTest {

        // Arrange
        val productId = UUID.randomUUID().toString()
        val mockProductEntity = MockProductEntityProvider.createMockProductEntity().copy(id = productId)

        // When
        `when`(productDao.get(productId)).thenReturn(flow { emit(mockProductEntity) })
        val flowResult = productRepository.getProductById(productId)

        // Then
        flowResult.collect { productModel ->
            assertEquals(mockProductEntity.asDomainModel(), productModel)
        }
    }

    @Test
    fun getProductById_returns_nothing_trows_error() = testScope.runTest {

        // Given
        val productId = UUID.randomUUID().toString()

        // When
        `when`(productDao.get(productId)).thenThrow(RuntimeException("Simulated error"))
        val flowResult = productRepository.getProductById(productId)

        // Then
        flowResult.collect() {
            assertEquals(emptyList<ProductModel>(), flowResult)
        }
    }

}