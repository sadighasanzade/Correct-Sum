package com.sadig.correctsum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.sadig.correctsum.ui.screen.SimpleSumGame
import com.sadig.correctsum.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewmodel.isGameRunning.collect{isGameRunning ->
                if(isGameRunning) {
                    viewmodel.resetGame()
                    setContent { SimpleSumGame(viewModel = viewmodel) }
                }
            }
        }
        viewmodel.getRandomNumbersPair()
    }
}
