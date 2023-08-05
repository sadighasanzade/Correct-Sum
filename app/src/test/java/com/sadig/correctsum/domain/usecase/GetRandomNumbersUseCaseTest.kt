package com.sadig.correctsum.domain.usecase

import com.sadig.correctsum.data.repository.FakeMainRepository
import com.sadig.correctsum.domain.repository.MainRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetRandomNumbersUseCaseTest{
    private lateinit var repository: MainRepository
    @Before
    fun setUp() {
        repository = FakeMainRepository()
    }

    @Test
    fun `CHECK IF BOTH SIDE OF PAIR IS IN RANGE OF 100`() = runBlocking {
        val pair = repository.getNumbersPair()
        assert(pair.first in 0..99 && pair.second in 0..99)
    }
}