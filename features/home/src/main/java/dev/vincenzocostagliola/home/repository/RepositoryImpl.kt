package dev.vincenzocostagliola.home.repository

import dev.vincenzocostagliola.data.datapersistence.DataPersistence
import dev.vincenzocostagliola.data.datapersistence.data.GetSettingsResultDP
import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.db.DodoDB
import dev.vincenzocostagliola.db.TodoDb
import dev.vincenzocostagliola.home.data.dto.SettingsDto.Companion.toDTO
import dev.vincenzocostagliola.home.data.dto.TodoDto
import dev.vincenzocostagliola.home.data.dto.result.GetActivityResultDto
import dev.vincenzocostagliola.home.data.dto.result.GetSettingsDtoResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import org.threeten.bp.OffsetDateTime
import timber.log.Timber

internal interface Repository {
    fun getAllActivities(): Flow<GetActivityResultDto>
    suspend fun getSettings(): GetSettingsDtoResult
}

internal class RepositoryImpl(
    private val db: DodoDB,
    private val dataPersistence: DataPersistence
) : Repository {


    override fun getAllActivities(): Flow<GetActivityResultDto> {
        return channelFlow {
            try {
                val list: List<TodoDto> = db.activitiesDao().getAllActivities().toDto()
                Timber.d("HomeScreen - Repository -  getAllActivities - dbList: $list")

                send(GetActivityResultDto.Success(list))
            } catch (e: Throwable) {
                Timber.d("HomeScreen - Repository -  getAllActivities - failure: $e")
                val result = GetActivityResultDto.Failure(e)
                send(result)
            }
        }
    }

    private fun List<TodoDb>.toDto(): List<TodoDto> {
        return this.map { activity ->
            with(activity) {
                TodoDto(
                    id = id,
                    title = title,
                    description = description,
                    status = status,
                    addedDate = OffsetDateTime.parse(addedDate)
                )
            }
        }
    }

    override suspend fun getSettings(): GetSettingsDtoResult {
        val result = dataPersistence.getSettings()

        return when (result) {
            is GetSettingsResultDP.Failure -> GetSettingsDtoResult.Failure(result.error)
            is GetSettingsResultDP.Success -> {
                GetSettingsDtoResult.Success(result.setting?.toDTO())
            }
        }

    }
}
