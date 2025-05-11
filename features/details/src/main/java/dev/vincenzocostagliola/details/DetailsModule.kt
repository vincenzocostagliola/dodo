package dev.vincenzocostagliola.details

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.db.DodoDB
import dev.vincenzocostagliola.details.repository.Repository
import dev.vincenzocostagliola.details.repository.RepositoryImpl
import dev.vincenzocostagliola.details.usecase.UseCase
import dev.vincenzocostagliola.details.usecase.UseCaseImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DetailsModule {

    @Provides
    @Singleton
    internal fun provideRepository(
        db: DodoDB
    ): Repository = RepositoryImpl(
        db = db
    )

    @Provides
    @Singleton
    internal fun provideUseCase(
        repository: Repository,
        errorManagement: ErrorManagement
    ): UseCase = UseCaseImpl(
        repository = repository,
        errorManagement = errorManagement
    )
}