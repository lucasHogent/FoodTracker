package com.project.foodtracker.data.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.project.foodtracker.data.database.FoodTrackerTestDatabase
import com.project.foodtracker.data.database.dao.IIngredientDao
import com.project.foodtracker.data.mock.MockIngredientEntityProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import java.io.IOException

/**
 * Unit tests for [IIngredientDao].
 *
 * @see IIngredientDao
 */
@RunWith(AndroidJUnit4::class)
class IngredientDaoTest {

    private lateinit var database: FoodTrackerTestDatabase
    private lateinit var ingredientDao: IIngredientDao

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, FoodTrackerTestDatabase::class.java)
            .build()

        ingredientDao = database.ingredientDao()
    }

    @After
    @Throws(IOException::class)
    fun close() {
        database.close()
    }

    /**
     * Test inserting an ingredient into the DAO and then getting it by its key.
     */
    @Test
    fun insertIngredient_And_GetIngredient() = runBlocking {
        // Given an ingredient
        val ingredient = MockIngredientEntityProvider.createTestIngredientEntity()
        val ingredientId = ingredient.ingredientId

        // When inserting the ingredient into the DAO
        ingredientDao.insert(ingredient)

        // And when getting the ingredient by id
        val ingredientFromDb = ingredientDao.get(ingredientId).firstOrNull()

        // Then the loaded data should match the original ingredient
        assertNotNull(ingredientFromDb)
        assertEquals(ingredientFromDb, ingredient)
    }

    /**
     * Test inserting a list of ingredients into the DAO and then getting all ingredients.
     */
    @Test
    fun insertAll_And_GetAllIngredients() = runBlocking {
        // Given a list of ingredients
        val ingredients = MockIngredientEntityProvider.createTestIngredientEntitiesList(5)

        // When inserting the list of ingredients into the DAO
        ingredientDao.insertAll(*ingredients.toTypedArray())

        // And when getting all ingredients from the DAO
        val ingredientsFromDb = ingredientDao.getAllIngredients()

        // Then the loaded data should match the original list of ingredients
        assertEquals(ingredientsFromDb.sortedBy { it.name }, ingredients.sortedBy { it.name })
    }

    /**
     * Test clearing all ingredients from the DAO.
     */
    @Test
    fun clearAllIngredients() = runBlocking {
        // Given a list of ingredients
        val ingredients = MockIngredientEntityProvider.createTestIngredientEntitiesList(5)

        // When inserting the list of ingredients into the DAO
        ingredientDao.insertAll(*ingredients.toTypedArray())

        // And when clearing all ingredients
        ingredientDao.clear()

        // And when getting all ingredients from the DAO after clearing
        val ingredientsFromDb = ingredientDao.getAllIngredients()

        // Then the loaded data should be an empty list
        assertTrue(ingredientsFromDb.isEmpty())
    }

    /**
     * Test getting an ingredient by name from the DAO.
     */
    @Test
    fun getIngredientByName() = runBlocking {
        // Given an ingredient
        val ingredient = MockIngredientEntityProvider.createTestIngredientEntity()

        // When inserting the ingredient into the DAO
        ingredientDao.insert(ingredient)

        // And when getting the ingredient by name
        val ingredientFromDb = ingredientDao.getByName(ingredient.name).firstOrNull()

        // Then the loaded data should match the original ingredient
        assertNotNull(ingredientFromDb)
        assertEquals(ingredientFromDb, ingredient)
    }

}

