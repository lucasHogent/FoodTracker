package com.project.foodtracker.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.project.foodtracker.data.database.dao.IProductDao
import com.project.foodtracker.data.database.entities.asDomain
import com.project.foodtracker.data.mock.MockProductDtoProvider
import com.project.foodtracker.data.mock.MockProductEntityProvider
import com.project.foodtracker.data.remote.IProductApiService
import com.project.foodtracker.domain.model.ProductDetailModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.util.UUID

/**
 * Unit tests for [ProductRepository].
 *
 * @see ProductRepository
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ProductRepositoryTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var productDao: IProductDao
    private lateinit var productApi: IProductApiService

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        productDao = mock(IProductDao::class.java)
        productApi = mock(IProductApiService::class.java)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        productRepository = ProductRepository(productDao, productApi, context)
    }

    /**
     * Test case for [ProductRepository.products].
     */
    @Test
    fun getAllProducts_returns_productList() = testScope.runTest {

        // Given
        val mockProductList = MockProductEntityProvider.createMockProductEntityList(10)
        `when`(productDao.getAllProducts()).thenReturn(mockProductList)

        // When
        val flowResult = productRepository.products

        // Then

        assertEquals(mockProductList.map { it.asDomain() }, mockProductList)

    }

    /**
     * Test case for [ProductRepository.getProductById].
     */
    @Test
    fun getProductById_returns_product() = testScope.runTest {

        // Arrange
        val productId = UUID.randomUUID().toString()
        val mockProductEntity = MockProductEntityProvider.createMockProductEntity()

        mockProductEntity.copy(productId = productId)

        // When
        // `when`(productDao.get(productId)).thenReturn(flow { emit(createMockProductDetailEntity()) })
        val flowResult = productRepository.getProductById(productId)

        // Then

        assertEquals(mockProductEntity.asDomain(), mockProductEntity)

    }

    /**
     * Test case for [ProductRepository.getProductById] when an error occurs.
     */
    @Test
    fun getProductById_returns_nothing_trows_error() = testScope.runTest {

        // Given
        val productId = UUID.randomUUID().toString()

        // When
        `when`(productDao.get(productId)).thenThrow(RuntimeException("Simulated error"))
        val flowResult = productRepository.getProductById(productId)

        // Then

        assertEquals(emptyList<ProductDetailModel>(), flowResult)

    }

    /**
     * Test case for [ProductRepository.refreshDatabase].
     */
    @Test
    fun getProductsFromApi_insertsProductEntities() = testScope.runTest {

        // Given
        val mockProductDto = MockProductDtoProvider.createMockProductDtoList(10)

        // When

        productRepository.refreshDatabase()

    }

}