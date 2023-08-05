package com.sadig.correctsum.data.repository

import com.sadig.correctsum.data.source.FakeDataSource
import com.sadig.correctsum.domain.repository.MainRepository
import kotlin.random.Random

class FakeMainRepository: MainRepository {
    override suspend fun getNumbersPair(): Pair<Int, Int> {
        val first = Random.nextInt(100)
        val second = Random.nextInt(100)
        return Pair(first, second)
    }
}