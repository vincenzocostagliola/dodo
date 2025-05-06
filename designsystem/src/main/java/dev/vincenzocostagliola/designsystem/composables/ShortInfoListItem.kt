package dev.vincenzocostagliola.designsystem.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

data class InfoUi(
    val id: Int,
    val description: String,
    val name: String,
    val status: String,
    val image: String?
)

@Composable
fun ShortInfoListItem(info: InfoUi, onClick: (Int) -> Unit) {
    Surface(
        onClick = { onClick(info.id) }
    ) {
        Column {
            ListItem(
                headlineContent = { Text(info.name) },
                supportingContent = { Text(info.description) },
                trailingContent = { Text(info.status) },
                leadingContent = {
                    info.image?.let {
                        AsyncImage(
                            model = info.image,
                            modifier = Modifier,
                            contentScale = ContentScale.Fit,
                            contentDescription = info.name
                        )
                    }
                }
            )
            HorizontalDivider()
        }
    }
}


@Preview
@Composable
private fun ShowShortInfoListItem() {
    val info: InfoUi = getFakeInfo()

    ShortInfoListItem(info) {}
}

private fun getFakeInfo(): InfoUi = InfoUi(
    id = 1,
    description = "This is Description",
    name = "What to do at home",
    status = "Doing",
    image = null
)
