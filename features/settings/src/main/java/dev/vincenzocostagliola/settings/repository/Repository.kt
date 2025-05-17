package dev.vincenzocostagliola.settings.repository

import dev.vincenzocostagliola.data.datapersistence.DataPersistence
import dev.vincenzocostagliola.data.datapersistence.data.GetSettingsResultDP
import dev.vincenzocostagliola.data.datapersistence.data.SettingsDP
import dev.vincenzocostagliola.settings.data.dto.SettingsDto
import dev.vincenzocostagliola.settings.data.dto.SettingsDto.Companion.toDTO
import dev.vincenzocostagliola.settings.data.dto.result.GetSettingsDtoResult

internal interface Repository {
    suspend fun getSettings(): GetSettingsDtoResult
    suspend fun saveSetting(setting: SettingsDto)
}

internal class RepositoryImpl(
    private val dataPersistence: DataPersistence
) : Repository {

    override suspend fun getSettings(): GetSettingsDtoResult {
        val result = dataPersistence.getSettings()

        return when (result) {
            is GetSettingsResultDP.Failure -> GetSettingsDtoResult.Failure(result.error)
            is GetSettingsResultDP.Success -> {
                GetSettingsDtoResult.Success(result.setting?.toDTO())
            }
        }

    }

    override suspend fun saveSetting(setting: SettingsDto) {
        dataPersistence.saveSettings(SettingsDP(orderSelected = setting.orderSelected))
    }
}