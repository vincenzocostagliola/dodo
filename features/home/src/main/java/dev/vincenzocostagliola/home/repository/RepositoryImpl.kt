package dev.vincenzocostagliola.home.repository

import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.db.DodoDB
import dev.vincenzocostagliola.db.TodoDb
import dev.vincenzocostagliola.home.data.dto.TodoDto
import dev.vincenzocostagliola.home.data.dto.result.GetActivityResultDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import org.threeten.bp.OffsetDateTime
import timber.log.Timber

internal interface Repository {
    fun getAllActivities(): Flow<GetActivityResultDto>
}

internal class RepositoryImpl(
    private val errorManagement: ErrorManagement,
    private val db: DodoDB
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
}
