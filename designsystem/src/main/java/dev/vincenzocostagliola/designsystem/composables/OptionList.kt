package dev.vincenzocostagliola.designsystem.composables


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Option(
    val value: String,
    val isSelected: Boolean,
    val isClickable: Boolean
)


@Composable
fun ColumnScope.OptionList(
    list: List<Option>,
    onOptionSelected: (Option) -> Unit,
    modifier: Modifier,
    titleText: String
) {

    Text(
        text = titleText,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
    )

    list.forEach { option ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { if (option.isClickable) onOptionSelected(option) else Unit }
        ) {
            RadioButton(
                selected = option.isSelected,
                onClick = null
            )
            Text(text = option.value)
        }
    }
}


@Preview
@Composable
fun PreviewOptionList() {
    var options by remember { mutableStateOf(getFakeList()) }
    Column(
        modifier = Modifier
            .widthIn(max = 480.dp)
            .verticalScroll(rememberScrollState())
    ) {
        this@Column.OptionList(
            list = options,
            onOptionSelected = { selected ->
                options = options.map {
                    it.copy(isSelected = it.value == selected.value)
                }
            },
            modifier = Modifier,
            titleText = "Title"
        )
    }
}

private fun getFakeList(): List<Option> {
    return listOf(
        Option(
            value = "data",
            isSelected = false,
            isClickable = true
        ),
        Option(
            value = "ora",
            isSelected = false,
            isClickable = true
        ),
        Option(
            value = "status",
            isSelected = false,
            isClickable = true
        ),
        Option(
            value = "nome",
            isSelected = true,
            isClickable = true
        ),
        Option(
            value = "nessuno",
            isSelected = false,
            isClickable = true
        )
    )
}
