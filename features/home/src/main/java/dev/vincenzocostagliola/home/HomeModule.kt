package dev.vincenzocostagliola.home

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.home.data.repository.Repository
import dev.vincenzocostagliola.home.data.repository.RepositoryImpl
import dev.vincenzocostagliola.home.usecase.HomeUseCase
import dev.vincenzocostagliola.home.usecase.HomeUseCaseImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class HomeModule {

    @Provides
    @Singleton
    internal fun provideRepository(
        errorManagement: ErrorManagement
    ): Repository = RepositoryImpl(
        errorManagement = errorManagement
    )
    @Provides
    @Singleton
    internal fun provideUseCase(
        repository: Repository
    ): HomeUseCase = HomeUseCaseImpl(
        repository = repository
    )
}