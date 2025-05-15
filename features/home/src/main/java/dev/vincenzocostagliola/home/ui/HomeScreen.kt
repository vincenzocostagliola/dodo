package dev.vincenzocostagliola.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.data.error.ErrorResources
import dev.vincenzocostagliola.designsystem.composables.ErrorDialog
import dev.vincenzocostagliola.designsystem.composables.InfoUi
import dev.vincenzocostagliola.designsystem.composables.ObserveLifecycle
import dev.vincenzocostagliola.designsystem.composables.Progress
import dev.vincenzocostagliola.designsystem.composables.ShortInfoListItem
import dev.vincenzocostagliola.designsystem.theme.ExtraLight
import dev.vincenzocostagliola.designsystem.theme.Purple40
import dev.vincenzocostagliola.designsystem.values.Dimens
import timber.log.Timber

@Composable
fun HomeScreen(viewModel: HomeViewModel, navigateToDetail: (Int?) -> Unit) {
    val state: State<HomeScreenState> = viewModel.homeScreenState.collectAsState()
    val viewState = state.value
    Timber.d("HomeScreen - ViewState: $viewState")

    ManageLifeCycleEvent(viewModel)

    ManageState(viewState, viewModel, navigateToDetail)
}

@Composable
private fun ManageLifeCycleEvent(viewModel: HomeViewModel) {
    viewModel.ObserveLifecycle(LocalLifecycleOwner.current.lifecycle)
}

@Composable
private fun ManageState(
    viewState: HomeScreenState,
    viewModel: HomeViewModel,
    navigateToDetail: (Int?) -> Unit
) {
    when (viewState) {
        is HomeScreenState.Error -> {
            ShowError(viewState.error.newResources) {
                viewModel.sendEvent(HomeScreenEvents.PerformDialogAction(it))
            }
        }

        HomeScreenState.Loading -> {
            Progress(true)
        }

        is HomeScreenState.Success -> {
            Progress(false)
            ShowList(
                viewState.list,
                onClick = { id ->
                    Timber.d("HomeScreen - App Navigation - sent = $id")
                    navigateToDetail(id)
                },
                addToDo = {
                    navigateToDetail(null)
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
private fun ShowList(list: List<InfoUi>, onClick: (Int) -> Unit, addToDo: () -> Unit) {
    Scaffold(
        modifier = Modifier
            .background(ExtraLight)
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = addToDo,
                containerColor = Purple40,
                contentColor = ExtraLight
            ) {
                Icon(Icons.Filled.Add, "")
            }
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = Dimens.XRegular)
                    .consumeWindowInsets(innerPadding)
            ) {
                items(list.size) { item ->
                    ShortInfoListItem(
                        info = list[item],
                        onClick = onClick
                    )
                }
            }
        }
    )
}