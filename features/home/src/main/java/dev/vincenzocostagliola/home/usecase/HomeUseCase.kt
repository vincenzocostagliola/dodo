package dev.vincenzocostagliola.home.usecase

import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.home.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.home.data.dto.result.GetActivityResultDto
import dev.vincenzocostagliola.home.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

internal interface HomeUseCase {
    fun getAllActivities(): Flow<GetActivityResult>
}

internal class HomeUseCaseImpl @Inject internal constructor(
    private val repository: Repository,
    private val errorManagement: ErrorManagement
) : HomeUseCase {

    override fun getAllActivities(): Flow<GetActivityResult> {
        Timber.d("HomeScreen - HomeUseCase - getAllActivities")

        return flow {
            repository.getAllActivities().collect { result ->
                Timber.d("HomeScreen - HomeUseCase -  getAllActivities : $result")

                when (result) {
                    is GetActivityResultDto.Failure -> {
                        emit(GetActivityResult.Failure(errorManagement.manageException(result.error)))
                    }

                    is GetActivityResultDto.Success -> {
                        emit(GetActivityResult.Success(result.list.map { it.toDomain() }))
                    }
                }


            }
        }
    }
}


