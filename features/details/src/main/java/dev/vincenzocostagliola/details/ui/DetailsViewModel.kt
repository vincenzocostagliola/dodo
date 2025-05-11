package dev.vincenzocostagliola.details.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.designsystem.composables.InfoUi
import dev.vincenzocostagliola.details.data.domain.Todo
import dev.vincenzocostagliola.details.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.details.ui.ScreenState.*
import dev.vincenzocostagliola.details.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

internal sealed class ScreenState {
    data object Loading : ScreenState()
    data class Success(val todo: InfoUi) : ScreenState()
    data class Error(val error: AppError) : ScreenState()
}

sealed class ScreenEvents {
    data class GetTodo(val id: Int) : ScreenEvents()
    data class PerformDialogAction(val dialogAction: DialogAction) : ScreenEvents()

}

@HiltViewModel
internal class DetailsViewModel @Inject internal constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<ScreenState> =
        MutableStateFlow(ScreenState.Loading)
    internal val screenState: StateFlow<ScreenState>
        get() = _screenState

    fun sendEvent(event: ScreenEvents) {
        Timber.d("DetailsScreen - screenEvents: $event")
        viewModelScope.launch() {
            when (event) {
                is ScreenEvents.GetTodo -> retrieveToDo(event.id)
                is ScreenEvents.PerformDialogAction -> performDialogAction(event.dialogAction)
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

            DialogAction.Retry -> retrieveToDo(savedId)
        }
    }

    private var savedId: Int = 0

    @VisibleForTesting
    private suspend fun retrieveToDo(id: Int) {

        savedId = id

        Timber.d("DetailsScreen - DetailsViewModel -  retrieveToDo")

        _screenState.update {
            ScreenState.Loading
        }

        useCase.getTodo(id).collect {
            Timber.d("DetailsScreen - DetailsViewModel -  retrieveToDo : $it")
            executeCollect(it)
        }
    }

    private fun executeCollect(result: GetActivityResult) {
        with(Dispatchers.Main) {
            when (result) {
                is GetActivityResult.Failure -> _screenState.update {
                    Error(result.error)
                }

                is GetActivityResult.Success -> _screenState.update {
                    Success(result.todo.toInfoUi())
                }

                GetActivityResult.NotFound -> {
                    _screenState.update {
                        //TODO replace with more precise error or manage a special ui state for not found
                        Error(AppError.GenericError)
                    }
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