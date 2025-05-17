package dev.vincenzocostagliola.details.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.designsystem.composables.FieldForm
import dev.vincenzocostagliola.designsystem.composables.InfoForm
import dev.vincenzocostagliola.designsystem.composables.InfoForm.Companion.getEmptyInfoForm
import dev.vincenzocostagliola.designsystem.composables.Option
import dev.vincenzocostagliola.details.data.domain.Todo
import dev.vincenzocostagliola.details.data.domain.Todo.Companion.getOptionList
import dev.vincenzocostagliola.details.data.domain.Todo.Companion.toOption
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
import kotlin.collections.filterNot

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
    data class OnStatusChange(val option: Option) : ScreenEvents()

}

@HiltViewModel
class DetailsViewModel @Inject internal constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<ScreenState> =
        MutableStateFlow(Loading)
    internal val screenState: StateFlow<ScreenState>
        get() = _screenState

    private val infoFormState = MutableStateFlow(InfoForm.getEmptyInfoForm(getOptionList()))

    init {
        observeInfoFormUpdates()
    }

    private fun observeInfoFormUpdates() {
        viewModelScope.launch {
            infoFormState.collect { updatedInfo ->
                Timber.d("DetailsScreen - observeInfoFormUpdates - infoFormState: $updatedInfo")
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
                is ScreenEvents.OnStatusChange -> onStatusChange(event.option)
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

    private var savedId: Int? = null

    @VisibleForTesting
    private suspend fun retrieveToDo(id: Int?) {

        savedId = id

        Timber.d("DetailsScreen - DetailsViewModel -  retrieveToDo - id: $id")

        showLoading()

        id?.let {
            useCase.getTodo(id).collect {
                Timber.d("DetailsScreen - DetailsViewModel -  retrieveToDo : $it")
                executeCollect(it)
            }
        } ?: run {
            addNewTodo()
        }
    }

    private fun addNewTodo() {
        infoFormState.update { getEmptyInfoForm(getOptionList()) }
        _screenState.update { Success(infoFormState.value) }
    }

    private fun onValueChanged(info: FieldForm) {
        infoFormState.update {
            it.copy(list = updateFieldForm(it, info))
        }
    }

    private fun onStatusChange(option: Option) {
        infoFormState.update {
            it.copy(statusOptions = updateSelectedOption(it, option))
        }
    }

    private fun updateSelectedOption(
        form: InfoForm,
        option: Option
    ): MutableList<Option> {
        return form.statusOptions
            .filterNot { it.value == option.value }
            .toMutableList()
            .apply { add(option) }
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
        Timber.d("DetailsScreen - DetailsViewModel -  manageModifyOrSave - readOnly: $readOnly")
        /*means save todo*/
        if (readOnly && noErrorsPresent(
                form = infoFormState.value,
                readOnly = readOnly
            )
        ) {
            saveTodo()
            _screenState.update { Success(infoFormState.value) }
        } else {
            _screenState.update { Success(infoFormState.value.copy(readOnly = readOnly)) }
        }
    }

    private fun noErrorsPresent(form: InfoForm, readOnly: Boolean): Boolean {
        val updated = form.toTodo().toInfoForm(readOnly = readOnly)
        infoFormState.update { updated }
        val noError = updated.list.all { !(it.isError) }
        Timber.d("DetailsScreen - DetailsViewModel -  noErrorsPresent: $noError")

        return noError
    }

    private fun saveTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.saveTodo(infoFormState.value.toTodo())
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