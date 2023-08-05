package com.sadig.correctsum.domain.repository

interface MainRepository {
    suspend fun getNumbersPair(): Pair<Int, Int>
}