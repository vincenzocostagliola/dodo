package dev.vincenzocostagliola.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.data.error.ErrorResources
import dev.vincenzocostagliola.designsystem.composables.ErrorDialog
import dev.vincenzocostagliola.designsystem.composables.FieldForm
import dev.vincenzocostagliola.designsystem.composables.Form
import dev.vincenzocostagliola.designsystem.composables.InfoForm
import dev.vincenzocostagliola.designsystem.composables.Option
import dev.vincenzocostagliola.designsystem.composables.Progress
import dev.vincenzocostagliola.designsystem.composables.TopBar
import dev.vincenzocostagliola.designsystem.theme.ExtraLight
import dev.vincenzocostagliola.designsystem.theme.Purple40
import dev.vincenzocostagliola.details.data.domain.Todo
import dev.vincenzocostagliola.details.ui.ScreenEvents.GetTodo
import timber.log.Timber

@Composable
fun DetailsScreen(viewModel: DetailsViewModel, idToShow: Int?, onBackPressed: () -> Unit) {
    val state: State<ScreenState> = viewModel.screenState.collectAsState()
    val viewState = state.value


    Timber.d("DetailsScreen - ViewState: $viewState")
    ManageState(
        viewState = viewState,
        viewModel = viewModel,
        onBackPressed = onBackPressed
    )


    LaunchedEffect(idToShow) {
        viewModel.sendEvent(GetTodo(idToShow))
    }
}

@Composable
private fun ManageState(
    viewState: ScreenState,
    viewModel: DetailsViewModel,
    onBackPressed: () -> Unit
) {
    when (viewState) {
        is ScreenState.Error -> {
            ShowError(viewState.error.newResources) {
                viewModel.sendEvent(ScreenEvents.PerformDialogAction(it))
            }
        }

        ScreenState.Loading -> {
            Progress(true)
        }

        is ScreenState.Success -> {
            var todo by remember { mutableStateOf<InfoForm>(viewState.todo) }

            Progress(false)
            ShowTodo(
                info = todo,
                onBackPressed = onBackPressed,
                modifyOrSave = {
                    viewModel.sendEvent(
                        ScreenEvents.ModifyOrSave(it)
                    )
                },
                onValueChange = { viewModel.sendEvent(ScreenEvents.OnValueChanged(it)) },
                onStatusChange = { option ->
                    val options = todo.statusOptions.map {
                        it.copy(isSelected = it.value == option.value)
                    }
                    todo.copy(statusOptions = options)
                    viewModel.sendEvent(ScreenEvents.OnStatusChange(option))
                }
            )
        }
    }
}


@Composable
private fun ShowError(newResources: ErrorResources, performAction: (DialogAction) -> Unit) {
    ErrorDialog(
        errorResources = newResources,
        performAction = performAction
    )
}

@Composable
private fun ShowTodo(
    info: InfoForm,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    modifyOrSave: (Boolean) -> Unit,
    onValueChange: (FieldForm) -> Unit,
    onStatusChange: (Option) -> Unit
) {

    Scaffold(
        modifier = modifier
            .background(ExtraLight),
        topBar = {
            TopBar(
                title = "",
                onBackButton = onBackPressed
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { modifyOrSave(info.readOnly.not()) },
                containerColor = Purple40,
                contentColor = ExtraLight
            ) {
                if (info.readOnly) {
                    Icon(Icons.Filled.Edit, "")
                } else {
                    Icon(Icons.Filled.Save, "")
                }
            }
        },
    ) {
        Form(
            info = info,
            modifier = Modifier.padding(it),
            onValueChange = { onValueChange(it) },
            onStatusChange = { onStatusChange(it) }
        )
    }
}