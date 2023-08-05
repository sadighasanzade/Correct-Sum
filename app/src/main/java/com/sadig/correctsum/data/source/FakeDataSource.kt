package com.sadig.correctsum.data.source

import kotlinx.coroutines.delay
import kotlin.random.Random

class FakeDataSource {
    suspend fun getRandomNumbersPair(): Pair<Int, Int> {
        val first: Int = Random.nextInt(100)
        val second: Int = Random.nextInt(100)
        return Pair(first, second)
    }
}