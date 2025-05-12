package dev.vincenzocostagliola.details.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.data.error.ErrorResources
import dev.vincenzocostagliola.designsystem.composables.ErrorDialog
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
    viewModel.sendEvent(GetTodo(idToShow))
    ManageState(viewState, viewModel, navigateBack)
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
private fun ShowTodo(info: InfoUi, onBackPressed: () -> Unit) {
    Scaffold(
        modifier = Modifier
            .background(ExtraLight),
        topBar = {
            TopBar(
                title = info.name,
                onBackButton = { onBackPressed }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimens.XRegular)
                    .consumeWindowInsets(innerPadding)
            ) {
                Form()
            }
        }
    )
}

@Composable
fun Form() {
    val focusManager = LocalFocusManager.current
    LazyColumn(
        modifier = Modifier.widthIn(max = 480.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        item {
            Photo()
        }
        item {
            var imageUrl = remember { mutableStateOf<String?>(null) }
            Box(Modifier.fillMaxWidth()) {
                AsyncImage(model = imageUrl, contentDescription = null)
            }
        }
        item {
            Field(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                ),
                label = "First name",
                imageVector = Icons.Outlined.Person
            )
        }
        item {
            Field(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                ),
                label = "Last name",
                imageVector = null
            )
        }
        item { Spacer(Modifier.height(4.dp)) }
        item {
            Field(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                label = "Phone number",
                imageVector = Icons.Outlined.Phone
            )
        }
        item { Spacer(Modifier.height(4.dp)) }
        item {
            Field(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                label = "Email",
                imageVector = Icons.Outlined.Email
            )
        }
        item { Spacer(Modifier.height(4.dp)) }
        item {
            Field(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                label = "Country",
                imageVector = Icons.Outlined.LocationOn
            )
        }
        item {

            Field(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                label = "Street address",
                imageVector = null
            )
        }
        item {
            Field(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                label = "City",
                imageVector = null
            )
        }
        item {
            Field(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                label = "Zip/postal code",
                imageVector = null
            )
        }
        item {
            Text(
                text = "Groups",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
            )
        }
        item {
            var selected by remember { mutableStateOf<Int?>(null) }
            val options = listOf("Family", "Friends", "Work", "Other")
            Column {
                options.forEachIndexed { i, option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .clickable { selected = i }
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        RadioButton(selected = selected == i, onClick = null)
                        Text(text = option)
                    }

                }
            }
        }
        item {
            Text(
                text = "Notification Options",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
            )
        }
        item {
            var selected by remember { mutableStateOf(false) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .clickable { selected = selected.not() }
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Checkbox(checked = selected, onCheckedChange = null)
                Text(text = "Override 'Do not Disturb'")
            }
        }
        item {
            var selected by remember { mutableStateOf(false) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .clickable { selected = !selected.not() }
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Checkbox(checked = selected, onCheckedChange = null)
                Text(text = "Block calls from this contact")
            }
        }
    }
}

@Composable
private fun Photo() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            onClick = { /* TODO */ },
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = CircleShape,
            modifier = Modifier.border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
        ) {
            Box(modifier = Modifier.size(96.dp), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Outlined.CameraAlt,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text("Add photo")
    }
}

@Composable
private fun Field(
    focusManager: FocusManager,
    keyboardOptions: KeyboardOptions,
    label: String,
    imageVector: ImageVector?
) {
    var text by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        if (imageVector != null) {
            Icon(imageVector, null)
        } else {
            Box(Modifier.size(24.dp))
        }

        OutlinedTextField(
            modifier = Modifier.weight(1f),
            label = { Text(label) },
            value = text,
            onValueChange = { text = it },
            trailingIcon = {
                AnimatedVisibility(
                    visible = text.isNotBlank(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { text = "" }) {
                        Icon(Icons.Outlined.Cancel, "Clear")
                    }
                }
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            },
            singleLine = true,
        )
    }
}


@Preview
@Composable
fun PreviewForm() {
    Form()
}