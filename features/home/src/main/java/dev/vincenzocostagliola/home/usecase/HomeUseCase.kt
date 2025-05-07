package dev.vincenzocostagliola.home.usecase

import dev.vincenzocostagliola.home.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.home.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal interface HomeUseCase {
    suspend fun getAllActivities(): Flow<GetActivityResult>
}

internal class HomeUseCaseImpl @Inject internal constructor(
    private val repository: Repository
) : HomeUseCase {

    override suspend fun getAllActivities(): Flow<GetActivityResult> {
        return flow {
            repository.getAllActivities().collect {
                GetActivityResult.Success(it.map { it.toDomain() })
            }
        }
    }
}


