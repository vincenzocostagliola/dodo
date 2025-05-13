package dev.vincenzocostagliola.designsystem.composables


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vincenzocostagliola.designsystem.R

data class InfoForm(
    val id: Int,
    val description: String,
    val name: String,
    val status: String,
    val image: String?,
    val readOnly : Boolean
)

@Composable
fun Form(info: InfoForm, modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current
    LazyColumn(
        modifier = modifier
            .widthIn(max = 480.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        item {
            FormField(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                ),
                label = stringResource(R.string.title),
                imageVector = null,
                readOnly = info.readOnly,
                textToShow = info.name
            )
        }
        item { Spacer(Modifier.height(4.dp)) }
        item {
            FormField(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                ),
                label = stringResource(R.string.description),
                imageVector = null,
                readOnly = info.readOnly,
                textToShow = info.description
            )
        }
        item {
            FormField(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                ),
                label = stringResource(R.string.status),
                imageVector = null,
                readOnly = info.readOnly,
                textToShow = info.status
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


@Preview
@Composable
fun PreviewForm() {
    Form(
        info = getFakeInfo(),
        modifier = Modifier
    )
}
private fun getFakeInfo(): InfoForm = InfoForm(
    id = 1,
    description = "This is Description",
    name = "What to do at home",
    status = "Doing",
    image = null,
    readOnly = true
)