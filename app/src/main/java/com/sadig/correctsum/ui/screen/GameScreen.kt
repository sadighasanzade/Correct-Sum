package com.sadig.correctsum.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sadig.correctsum.ui.theme.Teal200
import com.sadig.correctsum.ui.theme.background
import com.sadig.correctsum.ui.theme.backgroundEnd
import com.sadig.correctsum.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun SimpleSumGame(viewModel: MainViewModel) {
    val remainingTime = viewModel.timer.collectAsState()
    val gameState = viewModel.isGameRunning.collectAsState()
    //if time is up show results
    if (remainingTime.value == 0) {
        viewModel.setGameState(false)
    }
    if (!gameState.value) {
        //show result
        ResultScreen(mainViewModel = viewModel) {
            //reset
            viewModel.setGameState(true)
        }
    } else {
        //show game screen
        gameScreen(viewModel = viewModel)
    }
}

@Composable
private fun TimerDisplay(timer: Int) {
    Text(
        text = "Time: $timer",
        style = MaterialTheme.typography.h4,
        color = Color.White
    )
}

@Composable
fun countOfCorrectAnswers(count: Int, modifier: Modifier) {
    Text(
        modifier = modifier,
        text = "Correct Answers: $count",
        style = MaterialTheme.typography.h4,
        color = Color.White,
        textAlign = TextAlign.End
    )
}

@Composable
private fun QuestionCard(question: State<Pair<Int, Int>>) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .width(300.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color(0xFFEDEDED),
        elevation = 6.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "${question.value.first} + ${question.value.second}",
                style = MaterialTheme.typography.h3,
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AnswerCard(text: Int, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) Color(0xFF007BFF) else Color(0xFFEDEDED)
    val backgroundColor = if (isSelected) Teal200 else Color.White
    Card(
        modifier = Modifier
            .height(90.dp)
            .width(125.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, borderColor),
        backgroundColor = backgroundColor,
        elevation = 4.dp,
        onClick = {
            // Handle the answer selection here
            onClick.invoke()
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text.toString(),
                style = MaterialTheme.typography.h2,
                color = if (isSelected) Color.White else Color.Black
            )
        }
    }
}

@Composable
fun gameScreen(viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    // Timer state
    val remainingTime = viewModel.timer.collectAsState()
    // Question state
    val pair = viewModel.numbersPair.collectAsState()
    val question by remember { mutableStateOf(pair) }

    // Answer state
    val wrongAnswerVal = viewModel.wrongAnswerValue.collectAsState().value
    val answerVal = question.value.first + question.value.second

    //choosing position of correct answer randomly
    var isFirstAnswerCorrect by remember {
        mutableStateOf(Random.nextInt(10) % 2 == 0)
    }
    //for updating background of answer cards
    var isFirstButtonClicked by remember {
        mutableStateOf(false)
    }
    var isSeconButtonClicked by remember {
        mutableStateOf(false)
    }
    val countOfCorrectAnswers = viewModel.countOfCorrectAnswers.collectAsState()
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //count of correct answers
            countOfCorrectAnswers(
                count = countOfCorrectAnswers.value,
                modifier = Modifier
                    .padding(4.dp)
                    .semantics { testTag = "correctAnswer" }
            )

            Spacer(modifier = Modifier.height(200.dp))

            // Timer display
            TimerDisplay(remainingTime.value)

            Spacer(modifier = Modifier.height(16.dp))

            // Question card
            QuestionCard(question)

            Spacer(modifier = Modifier.height(16.dp))

            // Answer cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                AnswerCard(
                    text = if (isFirstAnswerCorrect) answerVal else wrongAnswerVal,
                    isSelected = isFirstButtonClicked
                ) {
                    isFirstButtonClicked = true
                    scope.launch {
                        delay(100)
                        viewModel.checkAnswer(
                            question.value,
                            if (isFirstAnswerCorrect) answerVal else wrongAnswerVal
                        )
                        isFirstButtonClicked = false
                        isSeconButtonClicked = false
                        isFirstAnswerCorrect = Random.nextInt(10) % 2 == 0
                    }

                }
                Spacer(modifier = Modifier.width(50.dp))
                AnswerCard(
                    text = if (isFirstAnswerCorrect) wrongAnswerVal else answerVal,
                    isSelected = isSeconButtonClicked
                ) {
                    isSeconButtonClicked = true
                    scope.launch {
                        delay(100)
                        viewModel.checkAnswer(
                            question.value,
                            if (isFirstAnswerCorrect) wrongAnswerVal else answerVal
                        )
                        isFirstButtonClicked = false
                        isSeconButtonClicked = false
                        isFirstAnswerCorrect = Random.nextInt(10) % 2 == 0
                    }
                }
            }

        }
    }
}


