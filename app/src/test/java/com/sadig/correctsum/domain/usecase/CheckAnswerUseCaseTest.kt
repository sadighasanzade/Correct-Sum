package com.sadig.correctsum.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class CheckAnswerUseCaseTest {
    private lateinit var pair: Pair<Int, Int>
    private var correctAnswer : Int = 0
    private var wrongAnswer : Int = 0

    @Before
    fun setUp() {
        pair = Pair(Random.nextInt(100), Random.nextInt(100))
        correctAnswer = pair.first + pair.second
    }
    @Test
    fun `TEST IF CHECKING STATEMENT IS CORRECT`() = runBlocking {
        assertEquals(pair.first + pair.second, correctAnswer)
        assertNotEquals(pair.first + pair.second, wrongAnswer)
    }
}