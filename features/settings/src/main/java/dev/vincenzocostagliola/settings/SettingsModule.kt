package dev.vincenzocostagliola.settings

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.vincenzocostagliola.data.datapersistence.DataPersistence
import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.settings.repository.Repository
import dev.vincenzocostagliola.settings.repository.RepositoryImpl
import dev.vincenzocostagliola.settings.usecase.SettingsUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class SettingsModule {

    @Provides
    @Singleton
    internal fun provideRepository(
        dataPersistence: DataPersistence
    ): Repository = RepositoryImpl(
        dataPersistence = dataPersistence
    )

    @Provides
    @Singleton
    internal fun provideUseCase(
        repository: Repository,
        errorManagement: ErrorManagement
    ): SettingsUseCase = SettingsUseCase(
        repository = repository,
        errorManagement = errorManagement
    )
}