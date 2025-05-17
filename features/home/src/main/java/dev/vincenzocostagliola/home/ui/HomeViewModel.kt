package dev.vincenzocostagliola.home.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.designsystem.composables.InfoUi
import dev.vincenzocostagliola.home.data.domain.Todo
import dev.vincenzocostagliola.home.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.home.usecase.HomeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

internal sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    data class Success(val list: List<InfoUi>) :
        HomeScreenState()

    data class Error(val error: AppError) : HomeScreenState()
}

sealed class HomeScreenEvents {
    data object GetAllActivities : HomeScreenEvents()
    data class PerformDialogAction(val dialogAction: DialogAction) : HomeScreenEvents()

}

@HiltViewModel
class HomeViewModel @Inject internal constructor(
    private val useCase: HomeUseCase
) : ViewModel(), DefaultLifecycleObserver {

    private val _homeScreenState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState.Loading)
    internal val homeScreenState: StateFlow<HomeScreenState>
        get() = _homeScreenState

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        sendEvent(HomeScreenEvents.GetAllActivities)
    }

    fun sendEvent(event: HomeScreenEvents) {
        Timber.d("HomeScreen - HomeScreenEvents: $event")
        viewModelScope.launch() {
            when (event) {
                is HomeScreenEvents.GetAllActivities -> getAllActivities()
                is HomeScreenEvents.PerformDialogAction -> performDialogAction(event.dialogAction)
            }
        }
    }

    private suspend fun performDialogAction(action: DialogAction) {
        when (action) {
            DialogAction.Leave -> Unit //TBD
            DialogAction.Quit -> {
                // Perform a logout if signed or go out from the app
                Unit
            }

            DialogAction.Retry -> getAllActivities()
        }
    }

    @VisibleForTesting
    private suspend fun getAllActivities() {
        Timber.d("HomeScreen - HomeViewModel -  getOrderedActivities")

        _homeScreenState.update {
            HomeScreenState.Loading
        }

        useCase.getOrderedActivities().collect {
            Timber.d("HomeScreen - HomeViewModel -  getOrderedActivities - collect: $it")
            executeCollect(it)
        }
    }

    private fun executeCollect(result: GetActivityResult) {
        with(Dispatchers.Main) {
            when (result) {
                is GetActivityResult.Failure -> _homeScreenState.update {
                    HomeScreenState.Error(result.error)
                }

                is GetActivityResult.Success -> _homeScreenState.update {
                    HomeScreenState.Success(result.list.map { it.toInfoUi() })
                }
            }
        }
    }
}

@VisibleForTesting
internal fun Todo.toInfoUi(): InfoUi {
    return InfoUi(
        id = id,
        description = description,
        name = title,
        status = status,
        image = null
    )
}