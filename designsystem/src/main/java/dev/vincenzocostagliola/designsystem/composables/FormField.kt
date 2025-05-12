package dev.vincenzocostagliola.designsystem.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vincenzocostagliola.designsystem.R

@Composable
fun FormField(
    focusManager: FocusManager,
    keyboardOptions: KeyboardOptions,
    label: String,
    imageVector: ImageVector?,
    textToShow: String,
    readOnly: Boolean
) {
    var text by remember { mutableStateOf(textToShow) }
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
                if (!readOnly) {
                    AnimatedVisibility(
                        visible = text.isNotBlank(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = { text = "" }) {
                            Icon(Icons.Outlined.Cancel, "Clear")
                        }
                    }
                }
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            },
            singleLine = false,
            readOnly = readOnly
        )
    }
}

@Preview
@Composable
fun ShowFormFieldReadOnly(){
    val focusManager = LocalFocusManager.current

    FormField(
        focusManager = focusManager,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Words
        ),
        label = stringResource(R.string.title),
        imageVector = null,
        readOnly = true,
        textToShow = "info.name"
    )
}

@Preview
@Composable
fun ShowFormFieldNotReadOnly(){
    val focusManager = LocalFocusManager.current

    FormField(
        focusManager = focusManager,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Words
        ),
        label = stringResource(R.string.title),
        imageVector = null,
        readOnly = false,
        textToShow = "info.name"
    )
}