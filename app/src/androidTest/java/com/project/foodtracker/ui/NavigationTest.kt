package com.project.foodtracker.ui

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import com.project.foodtracker.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
 class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    lateinit var navController: TestNavHostController


    @Before
    fun init() {
        hiltRule.inject()
        navController = TestNavHostController(composeTestRule.activity)
    }

    @Test
    fun isDefaultAtHomePageTest() {
        val homeLabel = composeTestRule.onNodeWithText("Home page")
        homeLabel.assertIsDisplayed()
    }

    @Test
    fun navigateToDiscoverTest() {

        val discoverBtn = composeTestRule.onNodeWithText("Discover").performClick()
        val discoverScreen = composeTestRule.onNodeWithText("Discover products")

        discoverBtn.assertHasClickAction()
        discoverScreen.assertIsDisplayed()
    }

    @Test
    fun navigateToFavoritesTest() {

        val favoritesBtn = composeTestRule.onNodeWithText("Favorites").performClick()
        val favoritesScreen = composeTestRule.onNodeWithText("Favorite products")

        favoritesBtn.assertHasClickAction()
        favoritesScreen.assertIsDisplayed()
    }

}
