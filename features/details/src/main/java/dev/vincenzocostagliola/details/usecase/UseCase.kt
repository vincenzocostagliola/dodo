package dev.vincenzocostagliola.details.usecase

import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.details.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.details.data.domain.result.GetActivityResult.*
import dev.vincenzocostagliola.details.data.dto.result.GetActivityResultDto
import dev.vincenzocostagliola.details.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

internal interface UseCase {
    fun getTodo(id :Int): Flow<GetActivityResult>
}

internal class UseCaseImpl @Inject internal constructor(
    private val repository: Repository,
    private val errorManagement: ErrorManagement
) : UseCase {

    override fun getTodo(id: Int): Flow<GetActivityResult> {
        Timber.d("HomeScreen - HomeUseCase - getAllActivities")

        return flow {
            repository.getTodo(id).collect { result ->
                Timber.d("HomeScreen - HomeUseCase -  getAllActivities : $result")

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
        }
    }
}


