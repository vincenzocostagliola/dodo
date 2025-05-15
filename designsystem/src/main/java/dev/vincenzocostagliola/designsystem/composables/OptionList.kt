package dev.vincenzocostagliola.designsystem.composables


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
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
    val value : String,
    val isSelected : Boolean
)


@Composable
fun OptionList(list: List<Option>, optionSelected: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .widthIn(max = 480.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        item {
            Text(
                text = "Sort By",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
            )
        }
        item {
            var selected by remember { mutableStateOf<Option?>(null) }
            Column {
                list.forEachIndexed { i, option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .clickable { selected = option }
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        RadioButton(selected = option.isSelected, onClick = { optionSelected(option.value)})
                        Text(text = option.value)
                    }

                }
            }
        }
}
}


@Preview
@Composable
fun PreviewOptionList() {
    OptionList(
        getFakeList(),
        optionSelected = {  }
    )
}

private fun getFakeList(): List<Option> {
   return listOf(
       Option(value = "data", isSelected = false),
       Option(value = "ora", isSelected = false),
       Option(value = "status", isSelected = false),
       Option(value = "nome", isSelected = true),
       Option(value = "nessuno", isSelected = false)
   )
}
