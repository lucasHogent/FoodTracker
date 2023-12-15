package com.project.foodtracker.data.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.project.foodtracker.data.database.FoodTrackerTestDatabase
import com.project.foodtracker.data.database.dao.IProductDao
import com.project.foodtracker.data.database.entities.ProductWithIngredientsCrossRef
 import com.project.foodtracker.data.mock.MockIngredientEntityProvider
import com.project.foodtracker.data.mock.MockProductEntityProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import java.io.IOException

/**
 * Unit tests for [IProductDao].
 *
 * @see IProductDao
 */
@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var database: FoodTrackerTestDatabase
    private lateinit var productDao: IProductDao

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, FoodTrackerTestDatabase::class.java)
            .build()

        productDao = database.productDao()
    }

    @After
    @Throws(IOException::class)
    fun close() {
        database.close()
    }

    /**
     * Test inserting a product into the DAO and then getting it by its key.
     */
    @Test
    fun insertProduct_And_GetProduct() = runBlocking {
        // Given a product
        val product = MockProductEntityProvider.createMockProductEntity()
        val productId = product.productId

        // When inserting the product into the DAO
        productDao.insert(product)

        // And when getting the product by id
        val productFromDb = productDao.get(productId)

        // Then the loaded data should match the original product
        assertEquals(productFromDb, product)

    }

    /**
     * Test inserting a list of products into the DAO and then getting all products.
     */
    @Test
    fun insertAll_And_GetAllProducts() = runBlocking {

        // Given a list of products
        val products = MockProductEntityProvider.createMockProductEntityList(10)

        // When inserting the list of products into the DAO
        productDao.insertAll(*products.toTypedArray())

        // And when getting all products from the DAO
        val productsFromDb = productDao.getAllProducts()

        // Then the loaded data should match the original list of products
        assertEquals(productsFromDb.sortedBy { p -> p.title }, products.sortedBy { p -> p.title })
    }

    /**
     * Test clearing all products from the DAO.
     */
    @Test
    fun clearAllProducts() = runBlocking {

        // Given a list of products
        val products = MockProductEntityProvider.createMockProductEntityList(10)

        // When inserting the list of products into the DAO
        productDao.insertAll(*products.toTypedArray())

        // And when clearing all products
        productDao.clear()

        // And when getting all products from the DAO after clearing
        val productsFromDb = productDao.getAllProducts()

        // Then the loaded data should be an empty list
        assert(productsFromDb.isEmpty())
    }

    /**
     * Test insert product linked to ingredients from the DAO.
     */
    @Test
    @Throws(IOException::class)
    fun insertProductWithIngredients_And_GetProductWithIngredients() = runBlocking {

        // Given a product and some ingredients
        val product = MockProductEntityProvider.createMockProductEntity()
        val ingredients = MockIngredientEntityProvider.createTestIngredientEntitiesList(10)

        // When Insert the product into the database
        productDao.insert(product)

        // And when insert the ingredients into the database
        database.ingredientDao().insertAll(*ingredients.toTypedArray())

        // And when insert the cross ref of the ingredients for that product into the database
        for(ingredient in ingredients){
            productDao.insert(ProductWithIngredientsCrossRef(product.productId, ingredient.ingredientId))
        }

        // And when retrieve the product from the database
        val retrievedFood = productDao.get(product.productId)

        // Assert that the retrieved product has the ingredients that were linked
        //assertEquals(retrievedFood.ingredients.sortedBy { i -> i.ingredientId }, ingredients.sortedBy { i -> i.ingredientId })

    }


}