package com.project.foodtracker.data.mock

 import com.project.foodtracker.data.remote.dto.IngredientDto
 import com.project.foodtracker.data.remote.dto.ProductApiResponse
 import com.project.foodtracker.data.remote.dto.ProductDetailDto
 import com.project.foodtracker.data.remote.dto.ProductDto
 import com.project.foodtracker.data.remote.dto.ServingsDto
 import java.util.UUID

/**
 * A provider object for creating mock instances of [ProductDetailDto] for testing purposes.
 *
 * @see ProductDetailDto
 * @see IngredientDto
 * @see ServingsDto
 */
object MockProductDtoProvider {
    /**
     * Creates a single test instance of [ProductDetailDto] with mock data.
     *
     * @return A test instance of [ProductDetailDto].
     */
    fun createMockProductDto(): ProductDetailDto {
        return ProductDetailDto(
            id = "123456",
            title = "Mock Product",
            breadcrumbs = listOf("Category1", "Category2"),
            imageType = "jpg",
            badges = listOf("Badge1", "Badge2"),
            importantBadges = listOf("ImportantBadge1", "ImportantBadge2"),
            ingredientCount = 5,
            generatedText = "Mock generated text",
            ingredients = listOf(
                IngredientDto("1", "Ingredient1", "Ingredient1", "High"),
                IngredientDto("2", "Ingredient2", "Ingredient2",  "Medium"),
                IngredientDto("3", "Ingredient3", "Ingredient3", "Low")
            ),
            likes = 10,
            aisle = "Aisle1",
            price = 15.5,
            servings = ServingsDto(2, 150, "g"),
            spoonacularScore = 75.5
        )
    }
    /**
     * Creates a list of test instances of [ProductDetailDto] with mock data.
     *
     * @param count The size of the list to create.
     * @return A list of test instances of [ProductDetailDto].
     */
    fun createMockProductDtoList(count: Int): List<ProductDetailDto> {
        val productList = mutableListOf<ProductDetailDto>()
        repeat(count) {
            productList.add(createMockProductDto())
        }
        return productList
    }

    fun createProductApiResponse(): ProductApiResponse {
        return ProductApiResponse(
            products = createProductDtoList(2),
            totalProducts = 2,
            type = "product",
            offset = 0,
            number = 2
        )
    }

    fun createProductDto() : ProductDto {
        return ProductDto(UUID.randomUUID().toString(),
            "Pizza Buddy: Frozen Pizza Dough, 16 Oz",
            "jpg")
    }

    fun createProductDtoList(count: Int): List<ProductDto> {
        val productList = mutableListOf<ProductDto>()
        repeat(count) {
            productList.add(createProductDto())
        }
        return productList
    }

}