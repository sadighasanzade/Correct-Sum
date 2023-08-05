package com.sadig.correctsum.domain.usecase

import com.sadig.correctsum.domain.repository.MainRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class GetRandomNumbersUseCase @Inject constructor(private val repository: MainRepository) {
    suspend fun getRandomNumbersPairs(): Pair<Int, Int> {
        val pair = repository.getNumbersPair()
        //dummy check :) it can be done by getting numbers from 1,100 range but this is fake business logic
        if (pair.first != 0 && pair.second != 0) {
            return pair
        }
        return getRandomNumbersPairs()
    }

    suspend fun getRandomNumber(): Int {
        return Random.nextInt(1, 200)
    }
}