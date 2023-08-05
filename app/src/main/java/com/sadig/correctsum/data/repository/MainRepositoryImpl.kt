package com.sadig.correctsum.data.repository

import com.sadig.correctsum.data.source.FakeDataSource
import com.sadig.correctsum.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val dataSource: FakeDataSource) :
    MainRepository {
    override suspend fun getNumbersPair(): Pair<Int, Int> {
        return dataSource.getRandomNumbersPair()
    }

}