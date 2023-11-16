package com.project.foodtracker.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.project.foodtracker.local.db.FoodTrackerTestDatabase
import com.project.foodtracker.data.database.entities.Product
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.UUID

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

        val food = Product(UUID.randomUUID().toString(), "Pizza")

        // Insert the product into the database
        database.productDao().insert(food)

        // Retrieve the product from the database
        val retrievedFood = database.productDao().get(food.id)

        // Assert that the retrieved product is not null and has the correct values
        assert(retrievedFood != null)
        assert(retrievedFood!!.id == food.id)
        assert(retrievedFood.name == food.name)
        assert(retrievedFood == food)
    }
}