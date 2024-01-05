package com.project.foodtracker.data.mock

import com.project.foodtracker.data.database.entities.ProductEntity
import com.project.foodtracker.data.database.entities.ServingsEntity
import kotlin.random.Random

/**
 * A provider class for creating mock instances of [ProductEntity] and [ServingsEntity] for testing purposes.
 *
 * @see ProductEntity
 * @see ServingsEntity
 */
class MockProductEntityProvider {
    companion object {
        /**
         * Creates a single test instance of [ProductEntity] with mock data.
         *
         * @return A test instance of [ProductEntity].
         */
        fun createMockProductEntity(): ProductEntity {
            return ProductEntity(
                productId = Random.nextInt(1, 1000).toString(),
                title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs " + Random.nextInt(1, 999).toString(),
                image = "https://spoonacular.com/recipeImages/716429-556x370.jpg",
                imageType = "jpg",
                servings = 2,
                readyInMinutes = 45,
                license = "CC BY-SA 3.0",
                sourceName = "Full Belly Sisters",
                sourceUrl = "http://fullbellysisters.blogspot.com/2012/06/pasta-with-garlic-scallions-cauliflower.html",
                spoonacularSourceUrl = "https://spoonacular.com/pasta-with-garlic-scallions-cauliflower-breadcrumbs-716429",
                healthScore = 19.0f,
                spoonacularScore = 83.0f,
                pricePerServing = 163.15f,
                cheap = false,
                creditsText = "Full Belly Sisters",
                dairyFree = false,
                gaps = "no",
                glutenFree = false,
                instructions = "",
                ketogenic = false,
                lowFodmap = false,
                occasions = emptyList(),
                sustainable = false,
                vegan = false,
                vegetarian = false,
                veryHealthy = false,
                veryPopular = false,
                weightWatcherSmartPoints = 17,
                dishTypes = listOf("lunch", "main course", "main dish", "dinner"),
                summary = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs might be a good recipe to expand your main course repertoire. One portion of this dish contains approximately <b>19g of protein</b>, <b>20g of fat</b>, and a total of <b>584 calories</b>. For <b>$1.63 per serving</b>, this recipe <b>covers 23%</b> of your daily requirements of vitamins and minerals. This recipe serves 2. It is brought to you by fullbellysisters.blogspot.com. 209 people were glad they tried this recipe. A mixture of scallions, salt and pepper, white wine, and a handful of other ingredients are all it takes to make this recipe so scrumptious. From preparation to the plate, this recipe takes approximately <b>45 minutes</b>. All things considered, we decided this recipe <b>deserves a spoonacular score of 83%</b>. This score is awesome. If you like this recipe, take a look at these similar recipes: <a href=\"https://spoonacular.com/recipes/cauliflower-gratin-with-garlic-breadcrumbs-318375\">Cauliflower Gratin with Garlic Breadcrumbs</a>, <a href=\"https://spoonacular.com/recipes/pasta-with-cauliflower-sausage-breadcrumbs-30437\">Pasta With Cauliflower, Sausage, & Breadcrumbs</a>, and <a href=\"https://spoonacular.com/recipes/pasta-with-roasted-cauliflower-parsley-and-breadcrumbs-30738\">Pasta With Roasted Cauliflower, Parsley, And Breadcrumbs</a>",
                deleted = false
            )
        }

        /**
         * Creates a list of test instances of [ProductEntity] with mock data.
         *
         * @param count The size of the list to create.
         * @return A list of test instances of [ProductEntity].
         */
        fun createMockProductEntityList(count: Int): List<ProductEntity> {
            val productList = mutableListOf<ProductEntity>()
            repeat(count) {
                productList.add(createMockProductEntity())
            }
            return productList
        }

    }
}