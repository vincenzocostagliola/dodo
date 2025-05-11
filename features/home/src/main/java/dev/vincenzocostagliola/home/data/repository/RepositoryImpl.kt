package dev.vincenzocostagliola.home.data.repository

import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.db.TodoDb
import dev.vincenzocostagliola.db.DodoDB
import dev.vincenzocostagliola.home.data.dto.TodoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

internal interface Repository {
    suspend fun getAllActivities(): Flow<List<TodoDto>>
}

internal class RepositoryImpl(
    private val errorManagement: ErrorManagement,
    private val db: DodoDB
) : Repository {

    override suspend fun getAllActivities(): Flow<List<TodoDto>> {
        return flow {
            //  val list: List<ActivityDto> = db.activitiesDao().getAllActivities().toDto()
            //TODO to remove is just for test
            val list: List<TodoDto> = listOf(
                TodoDto(
                    id = 9091,
                    title = "consectetuer",
                    description = "cubilia",
                    status = "aenean"
                ),
                TodoDto(
                    id = 9091,
                    title = "consectetuer",
                    description = "cubilia",
                    status = "aenean"
                ),
                TodoDto(
                    id = 9091,
                    title = "consectetuer",
                    description = "cubilia",
                    status = "aenean"
                ),
                TodoDto(
                    id = 9091,
                    title = "consectetuer",
                    description = "cubilia",
                    status = "aenean"
                ),
                TodoDto(
                    id = 9091,
                    title = "consectetuer",
                    description = "cubilia",
                    status = "aenean"
                ),
                TodoDto(
                    id = 9091,
                    title = "consectetuer",
                    description = "cubilia",
                    status = "aenean"
                ),
            )
            Timber.d("HomeScreen - Repository -  getAllActivities : $list")

            emit(list)
        }
    }

    private fun List<TodoDb>.toDto(): List<TodoDto> {
        return this.map { activity ->
            with(activity) {
                TodoDto(
                    id = id,
                    title = title,
                    description = description,
                    status = status
                )
            }
        }
    }
}