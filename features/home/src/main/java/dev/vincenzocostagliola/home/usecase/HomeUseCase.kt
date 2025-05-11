package dev.vincenzocostagliola.home.usecase

import dev.vincenzocostagliola.home.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.home.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

internal interface HomeUseCase {
    fun getAllActivities(): Flow<GetActivityResult>
}

internal class HomeUseCaseImpl @Inject internal constructor(
    private val repository: Repository
) : HomeUseCase {

    override fun getAllActivities(): Flow<GetActivityResult> {
        Timber.d("HomeScreen - HomeUseCase - getAllActivities")

        return flow {
            repository.getAllActivities().collect {
                Timber.d("HomeScreen - HomeUseCase -  getAllActivities : $it")
                emit(GetActivityResult.Success(it.map { it.toDomain() }))
            }
        }
    }
}


