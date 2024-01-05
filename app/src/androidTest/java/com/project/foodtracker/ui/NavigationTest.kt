package com.project.foodtracker.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.project.foodtracker.ui.navigation.NavComponent
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
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController


    @Before
    fun init() {
        hiltRule.inject()

        composeTestRule.setContent {
            val navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavComponent(navController, modifier = Modifier)
        }
    }
    @Test
    fun navigateToSecondActivity() {

    }

    @Test
    fun navigateBackToMainActivityFromSecondActivity() {

    }
}
