package dev.vincenzocostagliola.designsystem.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Progress(show: Boolean) {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(enabled = false) {}
    ) {
        if (show) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
fun ProgressPreviewVisible() {
    Progress(true)
}

@Preview
@Composable
fun ProgressPreviewNotVisible() {
    Progress(false)
}
