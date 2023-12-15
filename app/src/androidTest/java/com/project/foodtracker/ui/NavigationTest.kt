package com.project.foodtracker.ui

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.project.foodtracker.FoodTrackerApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    private val someTaskName: String = "some task name"

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

        }
    }

    @Test
    fun verifyStartDestination() {
        composeTestRule
            .onNodeWithText("FoodTrackerApp")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToFavorites() {
        composeTestRule
            .onNodeWithContentDescription("favorites")
            .performClick()
        composeTestRule
            .onNodeWithText("Favorites")
            .assertIsDisplayed()
    }




}