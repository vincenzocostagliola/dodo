package dev.vincenzocostagliola.settings.usecase

import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.settings.data.domain.result.GetSettingsResult
import dev.vincenzocostagliola.settings.data.dto.result.GetSettingsDtoResult
import dev.vincenzocostagliola.settings.repository.Repository
import timber.log.Timber

internal interface SettingsUseCase {
    suspend fun getSettings(): GetSettingsResult
}

internal class SettingsUseCaseImpl(
    val repository: Repository,
    val errorManagement: ErrorManagement
) : SettingsUseCase {

    override suspend fun getSettings(): GetSettingsResult {
        Timber.d("SettingScreen - SettingUseCase - getSettings")

        val result = repository.getSettings()
        return when (result) {
            is GetSettingsDtoResult.Failure -> {
                GetSettingsResult.Failure(
                    errorManagement.manageException(result.error)
                )
            }

            is GetSettingsDtoResult.Success -> {
                GetSettingsResult.Success(result.dto?.toDomain())
            }
        }
    }
}