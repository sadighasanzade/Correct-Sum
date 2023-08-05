package com.sadig.correctsum.di.module

import com.sadig.correctsum.data.source.FakeDataSource
import com.sadig.correctsum.domain.repository.MainRepository
import com.sadig.correctsum.domain.usecase.CheckAnswerUseCase
import com.sadig.correctsum.domain.usecase.GetRandomNumbersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CorrectSumComponent {

    @Singleton
    @Provides
    fun provideFakeDataSource() : FakeDataSource = FakeDataSource()

    @Singleton
    @Provides
    fun provideGetNumbersUseCase(repository: MainRepository): GetRandomNumbersUseCase {
        return GetRandomNumbersUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideCheckAnswerUseCase() : CheckAnswerUseCase = CheckAnswerUseCase()

}