package com.sadig.correctsum.ui.screen

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sadig.correctsum.MainActivity
import com.sadig.correctsum.ui.viewmodel.MainViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResultScreenKtTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeRule.activity.setContent {
            ResultScreen(mainViewModel = composeRule.activity.viewModels<MainViewModel>().value) {
                composeRule.activity.viewModels<MainViewModel>().value.setGameState(true)
            }
        }
    }

    @Test
    fun clickPlayAgain_navigateToGameScreen() {
        composeRule.onNodeWithTag("playAgain").performClick()
        composeRule.onNodeWithText(
            "correct",
            substring = true,
            ignoreCase = true,
            useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun clickPlayAgain_hidResultDialog() {
        composeRule.onNodeWithTag("playAgain").performClick()
        composeRule.onNodeWithText(
            "game Over",
            substring = true,
            ignoreCase = true,
            useUnmergedTree = true
        ).assertDoesNotExist()
    }
}