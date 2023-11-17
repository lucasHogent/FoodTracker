package com.project.foodtracker.data.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.project.foodtracker.data.mock.MockProductEntityProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ProductDatabaseTest {

    private lateinit var database: FoodTrackerTestDatabase
    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, FoodTrackerTestDatabase::class.java)
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }
    @Test
    @Throws(IOException::class)
    fun insertAndReadProduct() = runBlocking {

        val product = MockProductEntityProvider.createMockProductEntity()

        // Insert the product into the database
        database.productDao().insert(product)

        // Retrieve the product from the database
        val retrievedFood = database.productDao().get(product.productId).first()

        // Assert that the retrieved product is not null and has the correct values
        assert(retrievedFood.productId == product.productId)
        assert(retrievedFood.title == product.title)
        assert(retrievedFood == product)
    }
}