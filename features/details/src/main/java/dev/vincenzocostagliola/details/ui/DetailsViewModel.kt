package dev.vincenzocostagliola.details.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.designsystem.composables.FieldForm
import dev.vincenzocostagliola.designsystem.composables.InfoForm
import dev.vincenzocostagliola.details.data.domain.Todo.Companion.toTodo
import dev.vincenzocostagliola.details.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.details.ui.ScreenState.Error
import dev.vincenzocostagliola.details.ui.ScreenState.Loading
import dev.vincenzocostagliola.details.ui.ScreenState.Success
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
    data class Success(val todo: InfoForm) : ScreenState()
    data class Error(val error: AppError) : ScreenState()
}

sealed class ScreenEvents {
    data class GetTodo(val id: Int?) : ScreenEvents()
    data class ModifyOrSave(val modify: Boolean) : ScreenEvents()
    data class PerformDialogAction(val dialogAction: DialogAction) : ScreenEvents()
    data class OnValueChanged(val info: FieldForm) : ScreenEvents()

}

@HiltViewModel
class DetailsViewModel @Inject internal constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<ScreenState> =
        MutableStateFlow(Loading)
    internal val screenState: StateFlow<ScreenState>
        get() = _screenState

    private val infoFormState = MutableStateFlow(InfoForm.getEmptyInfoForm())

    init {
        observeInfoFormUpdates()
    }

    private fun observeInfoFormUpdates() {
        viewModelScope.launch {
            infoFormState.collect { updatedInfo ->
                Timber.d("DetailsScreen - infoFormState: $updatedInfo")
            }
        }
    }

    fun sendEvent(event: ScreenEvents) {
        Timber.d("DetailsScreen - screenEvents: $event")
        viewModelScope.launch() {
            when (event) {
                is ScreenEvents.GetTodo -> retrieveToDo(event.id)
                is ScreenEvents.PerformDialogAction -> performDialogAction(event.dialogAction)
                is ScreenEvents.ModifyOrSave -> manageModifyOrSave(event.modify)
                is ScreenEvents.OnValueChanged -> onValueChanged(event.info)
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
    private suspend fun retrieveToDo(id: Int?) {

        savedId = id ?: 0

        Timber.d("DetailsScreen - DetailsViewModel -  retrieveToDo")

        showLoading()

        id?.let {
            useCase.getTodo(id).collect {
                Timber.d("DetailsScreen - DetailsViewModel -  retrieveToDo : $it")
                executeCollect(it)
            }
        } ?: {
            _screenState.update {
                Error(AppError.GenericError)
            }
        }
    }


    private fun onValueChanged(info: FieldForm) {
        infoFormState.update {
            it.copy(list = updateFieldForm(it, info))
        }
    }

    private fun updateFieldForm(
        form: InfoForm,
        info: FieldForm
    ): MutableList<FieldForm> {
        return form.list
            .filterNot { it::class == info::class }
            .toMutableList()
            .apply { add(info) }
    }

    private fun manageModifyOrSave(readOnly: Boolean) {
        saveTodo(readOnly)

        _screenState.update {
            infoFormState.update {
                it.copy(readOnly = readOnly)
            }
            Success(infoFormState.value)
        }
    }

    private fun saveTodo(readOnly: Boolean) {
        if (readOnly) {
            Timber.d("DetailsScreen - DetailsViewModel -  manageModifyOrSave: $readOnly")
            viewModelScope.launch(Dispatchers.IO) {
                useCase.saveTodo(infoFormState.value.toTodo())
            }
        }
    }

    private fun showLoading() {
        _screenState.update {
            Loading
        }
    }

    private fun executeCollect(result: GetActivityResult) {
        with(Dispatchers.Main) {
            when (result) {
                is GetActivityResult.Failure -> _screenState.update {
                    Error(result.error)
                }

                is GetActivityResult.Success -> _screenState.update {
                    infoFormState.update { result.todo.toInfoForm(readOnly = true) }
                    Success(infoFormState.value)
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