package dev.vincenzocostagliola.designsystem.composables


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun Form() {
    val focusManager = LocalFocusManager.current
    LazyColumn(
        modifier = Modifier
            .widthIn(max = 480.dp),
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
            FormField(
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
            FormField(
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
            FormField(
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
            FormField(
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
            FormField(
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

            FormField(
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
            FormField(
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
            FormField(
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


@Preview
@Composable
fun PreviewForm() {
    Form()
}