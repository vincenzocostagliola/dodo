package dev.vincenzocostagliola.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.data.error.ErrorResources
import dev.vincenzocostagliola.designsystem.composables.ErrorDialog
import dev.vincenzocostagliola.designsystem.composables.InfoUi
import dev.vincenzocostagliola.designsystem.composables.Progress
import dev.vincenzocostagliola.designsystem.composables.ShortInfoListItem
import dev.vincenzocostagliola.designsystem.theme.ExtraLight
import dev.vincenzocostagliola.designsystem.values.Dimens
import timber.log.Timber

@Composable
fun DetailsScreen(viewModel: DetailsViewModel, navigateBack: () -> Unit) {
    val state: State<ScreenState> = viewModel.screenState.collectAsState()
    val viewState = state.value
    Timber.d("HomeScreen - ViewState: $viewState")

    ManageState(viewState, viewModel, navigateBack)
}

@Composable
private fun ManageState(
    viewState: ScreenState,
    viewModel: DetailsViewModel,
    navigateToDetail: () -> Unit
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
                onClick = { id ->
                    Timber.d("App Navigation - sent = $id")
                    navigateToDetail
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
private fun ShowTodo(info: InfoUi, onClick: (Int) -> Unit) {
    Scaffold(
        modifier = Modifier
            .background(ExtraLight),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimens.XRegular)
                    .consumeWindowInsets(innerPadding)
            ) {

                    ShortInfoListItem (
                        info = info,
                        onClick = onClick
                    )
            }
        }
    )
}