package com.sadig.correctsum.di.module

import com.sadig.correctsum.data.repository.MainRepositoryImpl
import com.sadig.correctsum.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryComponent {
    @Singleton
    @Binds
    abstract fun bindMainRepository(
        repositoryImpl: MainRepositoryImpl
    ): MainRepository
}