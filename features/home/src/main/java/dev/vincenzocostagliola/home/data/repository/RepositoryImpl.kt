package dev.vincenzocostagliola.home.data.repository

import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.db.ActivityDb
import dev.vincenzocostagliola.db.DodoDB
import dev.vincenzocostagliola.home.data.dto.ActivityDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal interface Repository {
    suspend fun getAllActivities(): Flow<List<ActivityDto>>
}

internal class RepositoryImpl(
    private val errorManagement: ErrorManagement,
    private val db: DodoDB
) : Repository {

    override suspend fun getAllActivities(): Flow<List<ActivityDto>> {
        return flow {
            val list: List<ActivityDto> = db.activitiesDao().getAllActivities().toDto()
            emit(list)
        }
    }

    private fun List<ActivityDb>.toDto(): List<ActivityDto> {
        return this.map { activity ->
            with(activity) {
                ActivityDto(
                    id = id,
                    title = title,
                    description = description,
                    status = status
                )
            }
        }
    }
}