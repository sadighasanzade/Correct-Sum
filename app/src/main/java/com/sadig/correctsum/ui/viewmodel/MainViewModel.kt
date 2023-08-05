package com.sadig.correctsum.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadig.correctsum.domain.usecase.CheckAnswerUseCase
import com.sadig.correctsum.domain.usecase.GetRandomNumbersUseCase
import com.sadig.correctsum.utils.Constants.GAME_DURATION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRandomNumbersUseCase: GetRandomNumbersUseCase,
    private val checkAnswerUseCase: CheckAnswerUseCase
) : ViewModel() {
    private val _numbersPair: MutableStateFlow<Pair<Int, Int>> = MutableStateFlow(Pair(0, 0))
    val numbersPair: StateFlow<Pair<Int, Int>> = _numbersPair

    private val _countOfCorrectAnswers: MutableStateFlow<Int> = MutableStateFlow(0)
    val countOfCorrectAnswers: StateFlow<Int> = _countOfCorrectAnswers

    private val _wrongAnswerValue: MutableStateFlow<Int> = MutableStateFlow(0)
    val wrongAnswerValue: StateFlow<Int> = _wrongAnswerValue
    
    private val _isGameRunning: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isGameRunning: StateFlow<Boolean> = _isGameRunning

    private val _timer: MutableStateFlow<Int> = MutableStateFlow(GAME_DURATION)
    val timer: StateFlow<Int> = _timer
    fun getRandomNumbersPair() = viewModelScope.launch {
        _numbersPair.emit(getRandomNumbersUseCase.getRandomNumbersPairs())
        _wrongAnswerValue.emit(getRandomNumbersUseCase.getRandomNumber())
    }

    fun checkAnswer(numbers: Pair<Int, Int>, answer: Int) = viewModelScope.launch {
        if (checkAnswerUseCase.checkStatement(numbers, answer)) {
            val tempValue = countOfCorrectAnswers.value
            _countOfCorrectAnswers.emit(tempValue + 1)
        }
        getRandomNumbersPair()
    }

    fun setGameState(isRunning: Boolean) = viewModelScope.launch {
        _isGameRunning.emit(isRunning)
    }

    fun resetGame() = viewModelScope.launch {
        _countOfCorrectAnswers.emit(0)
        _isGameRunning.emit(true)
        updateTimer(GAME_DURATION)
        getRandomNumbersPair()
        startTimer()
    }

    fun startTimer() = viewModelScope.launch {
        while (timer.value > 0) {
            delay(1000)
            updateTimer(timer.value - 1)
        }
    }
    fun updateTimer(remainingTime : Int) = viewModelScope.launch {
        _timer.emit(remainingTime)
    }
}