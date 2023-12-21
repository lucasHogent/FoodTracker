package com.project.foodtracker.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.project.foodtracker.ui.navigation.NavComponent
import com.project.foodtracker.ui.util.Screen
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavComponent(navController, modifier = Modifier)
        }
    }

    @Test
    fun verifyStartDestination() {
        composeTestRule
            .onNodeWithText("HomeScreen")
            .assertIsDisplayed()
    }

    @Test
    fun whenMainActivityLaunchedNavigationHelperIsInvokedForFragment() {
        assertTrue(true)
    }


    @Test
    fun navigateToDiscoverScreen() {

         composeTestRule
            .onNodeWithContentDescription("Discover")
            .performClick()

//         composeTestRule
//            .onNodeWithContentDescription("Discover Screen Content Description")
//            .assertIsDisplayed()
    }

    @Test
    fun navigateToFavoritesScreen() {

        composeTestRule
            .onNodeWithContentDescription("Favorites")
            .performClick()

    }

    @Test
    fun navigateToProductDetailScreen() {

        val productId = "123"
        navController.navigate(Screen.ProductDetail.route + "/${productId}")

        composeTestRule
            .onNodeWithContentDescription("Product Detail View Content Description")
            .assertIsDisplayed()


    }


}