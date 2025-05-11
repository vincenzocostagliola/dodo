package dev.vincenzocostagliola.home.data.repository

import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.db.DodoDB
import dev.vincenzocostagliola.db.TodoDb
import dev.vincenzocostagliola.home.data.dto.TodoDto
import dev.vincenzocostagliola.home.data.dto.result.GetActivityResultDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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
        return flow {
            val list: List<TodoDto> = db.activitiesDao().getAllActivities().toDto()
            Timber.d("HomeScreen - Repository -  getAllActivities - dbList: $list")

            emit(GetActivityResultDto.Success(list))
        }.catch { e ->
            Timber.d("HomeScreen - Repository -  getAllActivities - failure: $e")
            val result = GetActivityResultDto.Failure(errorManagement.manageException(e))
            emit(result)
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
