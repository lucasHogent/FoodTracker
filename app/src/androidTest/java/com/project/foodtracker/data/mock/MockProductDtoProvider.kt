package com.project.foodtracker.data.mock

import com.project.foodtracker.data.database.entities.ProductEntity
import com.project.foodtracker.data.remote.dto.product.CaloricBreakdownDto
import com.project.foodtracker.data.remote.dto.product.IngredientDto
import com.project.foodtracker.data.remote.dto.product.NutrientDto
import com.project.foodtracker.data.remote.dto.product.NutritionDto
import com.project.foodtracker.data.remote.dto.product.ProductDto
import com.project.foodtracker.data.remote.dto.product.ServingsDto

object MockProductDtoProvider {
    fun createMockProductDto(): ProductDto {
        return ProductDto(
            id = "123456",
            title = "Mock Product",
            breadcrumbs = listOf("Category1", "Category2"),
            imageType = "jpg",
            badges = listOf("Badge1", "Badge2"),
            importantBadges = listOf("ImportantBadge1", "ImportantBadge2"),
            ingredientCount = 5,
            generatedText = "Mock generated text",
            ingredients = listOf(
                IngredientDto("1", "Ingredient1",  "High"),
                IngredientDto("2", "Ingredient2",  "Medium"),
                IngredientDto("3", "Ingredient3",  "Low")
            ),
            likes = 10,
            aisle = "Aisle1",
            nutrition = NutritionDto(
                listOf(
                    NutrientDto( "Nutrient1", 15, "g", 10.5),
                    NutrientDto( "Nutrient2", 20, "mg", 8.0),
                    NutrientDto( "Nutrient3", 10, "g", 5.5)
                ),
                CaloricBreakdownDto(20.0, 30.0, 50.0)
            ),
            price = 15,
            servings = ServingsDto(2, 150, "g"),
            spoonacularScore = 75
        )
    }

    fun createMockProductDtoList(count: Int): List<ProductDto> {
        val productList = mutableListOf<ProductDto>()
        repeat(count) {
            productList.add(createMockProductDto())
        }
        return productList
    }


}