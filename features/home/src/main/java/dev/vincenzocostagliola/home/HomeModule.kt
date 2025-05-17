package dev.vincenzocostagliola.home

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.vincenzocostagliola.data.datapersistence.DataPersistence
import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.db.DodoDB
import dev.vincenzocostagliola.home.repository.Repository
import dev.vincenzocostagliola.home.repository.RepositoryImpl
import dev.vincenzocostagliola.home.usecase.HomeUseCase
import dev.vincenzocostagliola.home.usecase.HomeUseCaseImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class HomeModule {

    @Provides
    @Singleton
    internal fun provideRepository(
        db: DodoDB,
        dataPersistence: DataPersistence
    ): Repository = RepositoryImpl(
        db = db,
        dataPersistence = dataPersistence
    )

    @Provides
    @Singleton
    internal fun provideUseCase(
        repository: Repository,
        errorManagement: ErrorManagement
    ): HomeUseCase = HomeUseCaseImpl(
        repository = repository,
        errorManagement = errorManagement
    )
}