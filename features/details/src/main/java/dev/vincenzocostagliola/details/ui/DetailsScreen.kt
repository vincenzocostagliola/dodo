package dev.vincenzocostagliola.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.data.error.ErrorResources
import dev.vincenzocostagliola.designsystem.composables.ErrorDialog
import dev.vincenzocostagliola.designsystem.composables.Form
import dev.vincenzocostagliola.designsystem.composables.InfoForm
import dev.vincenzocostagliola.designsystem.composables.InfoUi
import dev.vincenzocostagliola.designsystem.composables.Progress
import dev.vincenzocostagliola.designsystem.composables.TopBar
import dev.vincenzocostagliola.designsystem.theme.ExtraLight
import dev.vincenzocostagliola.designsystem.values.Dimens
import dev.vincenzocostagliola.details.ui.ScreenEvents.GetTodo
import timber.log.Timber

@Composable
fun DetailsScreen(viewModel: DetailsViewModel, idToShow: Int?, navigateBack: () -> Unit) {
    val state: State<ScreenState> = viewModel.screenState.collectAsState()
    val viewState = state.value
    Timber.d("DetailsScreen - ViewState: $viewState")
    ManageState(viewState, viewModel, navigateBack)

    LaunchedEffect(idToShow) {
        viewModel.sendEvent(GetTodo(idToShow))
    }
}

@Composable
private fun ManageState(
    viewState: ScreenState,
    viewModel: DetailsViewModel,
    navigateBack: () -> Unit
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
            Progress(false)
            ShowTodo(
                viewState.todo,
                onBackPressed = { navigateBack }
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
private fun ShowTodo(info: InfoForm, onBackPressed: () -> Unit) {
    Scaffold(
        modifier = Modifier
            .background(ExtraLight),
        topBar = {
            TopBar(
                title = info.name,
                onBackButton =  onBackPressed
            )
        },
        content = { innerPadding ->
            Surface(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimens.XRegular)
                    .consumeWindowInsets(innerPadding)

            ) { Form(info) }
        }
    )
}