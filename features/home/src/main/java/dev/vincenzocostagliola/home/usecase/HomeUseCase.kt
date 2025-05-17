package dev.vincenzocostagliola.home.usecase

import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.home.data.domain.SettingsDomain.OrderBy
import dev.vincenzocostagliola.home.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.home.data.domain.result.GetSettingsResult
import dev.vincenzocostagliola.home.data.dto.result.GetActivityResultDto
import dev.vincenzocostagliola.home.data.dto.result.GetSettingsDtoResult
import dev.vincenzocostagliola.home.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

internal interface HomeUseCase {
    fun getOrderedActivities(): Flow<GetActivityResult>
}

internal class HomeUseCaseImpl @Inject internal constructor(
    private val repository: Repository,
    private val errorManagement: ErrorManagement,
    private val appScope: CoroutineScope
) : HomeUseCase {

    private fun getAllActivities(): Flow<GetActivityResult> {
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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getOrderedActivities(): Flow<GetActivityResult> {
        return getSettings().flatMapMerge { settingsResult ->
            Timber.d("HomeScreen - HomeUseCase -  getOrderedActivities - settingsResult: $settingsResult")

            val orderBy : OrderBy = when (settingsResult) {
                is GetSettingsResult.Failure -> OrderBy.NOT_ORDERED
                is GetSettingsResult.Success -> settingsResult.settings?.orderSelected
                    ?: OrderBy.NOT_ORDERED
            }

            getAllActivities().map { activityResult ->
                Timber.d("HomeScreen - HomeUseCase -  getOrderedActivities - activityResult: $activityResult")

                when (activityResult) {
                    is GetActivityResult.Failure -> activityResult
                    is GetActivityResult.Success -> {
                        val orderedList = activityResult.list.sortedWith(
                            when (orderBy) {
                                OrderBy.DATE -> compareBy { it.addedDate } // Replace with actual date field if available
                                OrderBy.NAME -> compareBy { it.title.lowercase() }
                                OrderBy.STATUS -> compareBy { it.status.lowercase() }

                                OrderBy.REVERSED_DATE -> compareByDescending { it.id } // Replace with actual date
                                OrderBy.REVERSED_NAME -> compareByDescending { it.title.lowercase() }
                                OrderBy.REVERSED_STATUS -> compareByDescending { it.status.lowercase() }

                                OrderBy.NOT_ORDERED -> null
                            } ?: Comparator { _, _ -> 0 }
                        )

                        GetActivityResult.Success(orderedList)
                    }
                }
            }
        }
    }


    private val settingsFlow: StateFlow<GetSettingsResult> = flow {
        emit(getSettingsSync()) // Call the suspend version
    }.stateIn(
        scope = appScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = GetSettingsResult.Failure(AppError.GenericError)
    )

    private suspend fun getSettingsSync(): GetSettingsResult {
        val result = repository.getSettings()
        return when (result) {
            is GetSettingsDtoResult.Failure -> {
                val error = errorManagement.manageException(result.error)
                Timber.d("HomeScreen - getSettings Failure: $error")
                GetSettingsResult.Failure(error)
            }

            is GetSettingsDtoResult.Success -> {
                val domainResult = result.dto?.toDomain()
                Timber.d("HomeScreen - getSettings Success: $domainResult")
                GetSettingsResult.Success(domainResult)
            }
        }
    }

    private  fun getSettings(): Flow<GetSettingsResult> = settingsFlow
}


