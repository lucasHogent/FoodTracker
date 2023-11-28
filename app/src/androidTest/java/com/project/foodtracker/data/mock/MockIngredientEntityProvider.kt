package com.project.foodtracker.data.mock

import com.project.foodtracker.data.database.entities.IngredientEntity
import java.util.UUID
import kotlin.random.Random


/**
 * A provider class for creating mock instances of [IngredientEntity] for testing purposes.
 *
 * @see IngredientEntity
 */
class MockIngredientEntityProvider {
    companion object {
        /**
         * Creates a single test instance of [IngredientEntity] with random data.
         *
         * @return A test instance of [IngredientEntity].
         */
        fun createTestIngredientEntity(): IngredientEntity {
            val id = UUID.randomUUID().toString()
            val description = "Test Description"
            val name = "Test Ingredient ${Random.nextInt()}"
            val safetyLevel = "Test Safety Level"

            return IngredientEntity(id, description, name, safetyLevel)
        }

        /**
         * Creates a list of test instances of [IngredientEntity] with random data.
         *
         * @param size The size of the list to create.
         * @return A list of test instances of [IngredientEntity].
         */
        fun createTestIngredientEntitiesList(size: Int): List<IngredientEntity> {
            return List(size) { createTestIngredientEntity() }
        }
    }
}