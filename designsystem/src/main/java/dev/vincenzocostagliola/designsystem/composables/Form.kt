package dev.vincenzocostagliola.designsystem.composables


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
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
    val readOnly: Boolean,
    val list: List<FieldForm>
)

sealed class FieldForm {
    abstract val text: String
    abstract val singleLine: Boolean
    abstract val isError: Boolean


    data class Title(
        override val text: String,
        override val singleLine: Boolean,
        override val isError: Boolean
    ) : FieldForm()

    data class Description(
        override val text: String,
        override val singleLine: Boolean,
        override val isError: Boolean
    ) : FieldForm()

    data class Status(
        override val text: String,
        override val singleLine: Boolean,
        override val isError: Boolean
    ) : FieldForm()

    fun updateFieldFormWithNewText(text: String): FieldForm {
        return when (this) {
            is Description -> Description(
                text,
                singleLine = singleLine,
                isError = isError
            )

            is Status -> Status(
                text,
                singleLine = singleLine,
                isError = isError
            )

            is Title -> Title(
                text,
                singleLine = singleLine,
                isError = isError
            )
        }
    }
}

@Composable
fun Form(
    info: InfoForm,
    modifier: Modifier = Modifier,
    onValueChange: (FieldForm) -> Unit
) {
    val focusManager = LocalFocusManager.current
    LazyColumn(
        modifier = modifier
            .widthIn(max = 480.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        items(info.list.size) { item ->
            FormField(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                ),
                label = stringResource(R.string.title),
                imageVector = null,
                info = info.list[item],
                onValueChange = { onValueChange(it) }
            )
        }
    }
}


@Preview
@Composable
fun PreviewForm() {
    Form(
        info = getFakeInfo(),
        modifier = Modifier,
        onValueChange = {}
    )
}

private fun getFakeInfo(): InfoForm = InfoForm(
    id = 4884,
    readOnly = false,
    list = listOf(
        FieldForm.Title(
            text = "title",
            singleLine = false,
            isError = false,
        ),
        FieldForm.Description(
            text = "description",
            singleLine = false,
            isError = false,
        ),
        FieldForm.Status(
            text = "title",
            singleLine = false,
            isError = false,
        )
    )
)