package com.sadig.correctsum.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sadig.correctsum.ui.theme.Teal200
import com.sadig.correctsum.ui.theme.background
import com.sadig.correctsum.ui.theme.backgroundEnd
import com.sadig.correctsum.ui.viewmodel.MainViewModel

@Composable
fun ResultScreen(mainViewModel: MainViewModel, onReset: () -> Unit) {
    val count = mainViewModel.countOfCorrectAnswers.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            background, backgroundEnd
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .height(300.dp)
                    .width(300.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                backgroundColor = Color(0xFFEDEDED),
                elevation = 16.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.semantics { testTag = "gameOver" },
                        textAlign = TextAlign.Center,
                        text = "Game over! You have ${count.value} correct answers",
                        style = MaterialTheme.typography.h6,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedButton(onClick = { onReset.invoke() }, modifier = Modifier.semantics { testTag = "playAgain" }) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = "Play again",
                            style = MaterialTheme.typography.h5,
                            color = Teal200
                        )
                    }
                }
            }
        }
    }
}