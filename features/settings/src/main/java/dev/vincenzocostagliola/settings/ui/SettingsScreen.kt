package dev.vincenzocostagliola.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.data.error.ErrorResources
import dev.vincenzocostagliola.designsystem.composables.ErrorDialog
import dev.vincenzocostagliola.designsystem.composables.Option
import dev.vincenzocostagliola.designsystem.composables.OptionList
import dev.vincenzocostagliola.designsystem.composables.Progress
import dev.vincenzocostagliola.designsystem.composables.TopBar
import dev.vincenzocostagliola.designsystem.theme.ExtraLight
import dev.vincenzocostagliola.settings.R
import timber.log.Timber

@Composable
fun SettingsScreen(viewModel: SettingsScreenViewModel, onBackPressed: () -> Unit) {
    val state: State<ScreenState> = viewModel.screenState.collectAsState()
    val viewState = state.value

    Timber.d("SettingsScreen - ViewState: $viewState")
    ManageState(
        viewState = viewState,
        viewModel = viewModel,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun ManageState(
    viewState: ScreenState,
    viewModel: SettingsScreenViewModel,
    onBackPressed: () -> Unit
) {


    when (viewState) {
        is ScreenState.Error -> {
            ShowError(viewState.error.newResources) {
                viewModel.sendEvent(SettingsScreenEvents.PerformDialogAction(it))
            }
        }

        ScreenState.Loading -> {
            Progress(true)
        }

        is ScreenState.Success -> {
            var options by remember { mutableStateOf<List<Option>>(viewState.list) }
            Progress(false)
            ShowSettings(
                optionList = options,
                onBackPressed = onBackPressed,
                onValueChange = { option ->
                    options = options.map {
                        it.copy(isSelected = it.value == option.value)
                    }
                    viewModel.sendEvent(SettingsScreenEvents.SaveSettings(option))
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
private fun ShowSettings(
    optionList: List<Option>,
    onBackPressed: () -> Unit,
    onValueChange: (Option) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier
            .background(ExtraLight),
        topBar = {
            TopBar(
                title = stringResource(R.string.settings),
                onBackButton = onBackPressed
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .widthIn(max = 480.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            item {
                this@LazyColumn.OptionList(
                    list = optionList,
                    onOptionSelected = { onValueChange(it) },
                    modifier = Modifier.padding(it),
                    titleText = stringResource(R.string.sort_by),
                )
            }
        }
    }
}