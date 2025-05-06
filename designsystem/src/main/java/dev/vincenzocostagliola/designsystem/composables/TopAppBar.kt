@file:OptIn(ExperimentalMaterial3Api::class)

package dev.vincenzocostagliola.designsystem.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.vincenzocostagliola.designsystem.theme.ExtraLight
import dev.vincenzocostagliola.designsystem.R


@Composable
fun TopBar(title: String, onBackButton: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onBackButton
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_toolbar_icon),
                    contentDescription = "",
                    tint = Color(0xFF434C59)
                )
            }
        },
        title = {
            Text(
                text = title,
                style = heading4
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = ExtraLight)
    )
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar(title = "Awesome Top App Bar") {
    }
}
