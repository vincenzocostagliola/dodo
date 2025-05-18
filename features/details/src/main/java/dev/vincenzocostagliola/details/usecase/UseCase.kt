package dev.vincenzocostagliola.details.usecase

import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.details.data.domain.Todo
import dev.vincenzocostagliola.details.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.details.data.domain.result.GetActivityResult.*
import dev.vincenzocostagliola.details.data.dto.result.GetActivityResultDto
import dev.vincenzocostagliola.details.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.threeten.bp.OffsetDateTime
import timber.log.Timber
import javax.inject.Inject

internal interface UseCase {
    fun getTodo(id :Int): Flow<GetActivityResult>
    suspend fun saveTodo(todo: Todo)
}

internal class UseCaseImpl @Inject internal constructor(
    private val repository: Repository,
    private val errorManagement: ErrorManagement
) : UseCase {

    override fun getTodo(id: Int): Flow<GetActivityResult> {
        Timber.d("DetailsScreen - UseCase - getTodo")

        return flow {
            repository.getTodo(id).collect { result ->
                Timber.d("DetailsScreen - UseCase -  getTodo : $result")

                when (result) {
                    is GetActivityResultDto.Failure -> {
                        emit(Failure(errorManagement.manageException(result.error)))
                    }

                    is GetActivityResultDto.Success -> {
                        emit(Success(result.todo.toDomain()))
                    }

                    GetActivityResultDto.NotFound -> {
                        emit(NotFound)
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveTodo(todo: Todo) {
        Timber.d("DetailsScreen - UseCase - saveTodo")
        repository.saveTodo(todo.toDto(OffsetDateTime.now()))
    }
}


