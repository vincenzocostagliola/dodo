package dev.vincenzocostagliola.details.repository

import dev.vincenzocostagliola.db.DodoDB
import dev.vincenzocostagliola.db.TodoDb
import dev.vincenzocostagliola.details.data.dto.TodoDto
import dev.vincenzocostagliola.details.data.dto.result.GetActivityResultDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import org.threeten.bp.OffsetDateTime
import timber.log.Timber

internal interface Repository {
    fun getTodo(id: Int): Flow<GetActivityResultDto>
    suspend fun saveTodo(todoDto: TodoDto)
}

internal class RepositoryImpl(
    private val db: DodoDB
) : Repository {


    override fun getTodo(id: Int): Flow<GetActivityResultDto> {
        return channelFlow {
            try {
                val todo: TodoDto? = db.activitiesDao().getTodo(id)?.toDto()
                Timber.d("DetailsScreen - Repository -  getTodo - db: $todo")

                if (todo != null) {
                    send(GetActivityResultDto.Success(todo))
                } else {
                    send(GetActivityResultDto.NotFound)
                }

            } catch (e: Throwable) {
                Timber.d("DetailsScreen - Repository -  getTodo - failure: $e")
                val result = GetActivityResultDto.Failure(e)
                send(result)
            }
        }
    }

    override suspend fun saveTodo(todoDto: TodoDto) {
        Timber.d("DetailsScreen - Repository -  saveTodo - db: $todoDto")
        db.activitiesDao().insertTodo(todoDto.toTodoDb())
    }



    private fun TodoDb.toDto(): TodoDto {
        return TodoDto(
            id = id,
            title = title,
            description = description,
            status = status,
            addedDate = OffsetDateTime.parse(addedDate)
        )
    }
}
