package dev.vincenzocostagliola.settings.repository

import dev.vincenzocostagliola.data.datapersistence.DataPersistence
import dev.vincenzocostagliola.settings.data.dto.result.GetSettingsDtoResult

internal interface Repository {
    suspend fun getSettings(): GetSettingsDtoResult
}

internal class RepositoryImpl(
    private val dataPersistence: DataPersistence
) : Repository {

    override suspend fun getSettings(): GetSettingsDtoResult {
        val result = dataPersistence.getSettings()


    }

}