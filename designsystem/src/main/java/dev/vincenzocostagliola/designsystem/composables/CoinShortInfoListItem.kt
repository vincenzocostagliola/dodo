package dev.vincenzocostagliola.designsystem.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

data class CoinUi(
    val currentPrice: Double,
    val id: String,
    val image: String,
    val marketCap: Double,
    val marketCapRank: Int,
    val name: String,
)

@Composable
fun CoinShortInfoListItem(coin: CoinUi, onClick: (String) -> Unit) {
    Surface(
        onClick = { onClick(coin.id) }
    ) {
        Column {
            ListItem(
                headlineContent = { Text(coin.name) },
                supportingContent = { Text(coin.currentPrice.toString()) },
                trailingContent = { Text(coin.marketCapRank.toString()) },
                leadingContent = {
                    AsyncImage(
                        model = coin.image,
                        modifier = Modifier,
                        contentScale = ContentScale.Fit,
                        contentDescription = coin.name
                    )
                }
            )
            HorizontalDivider()

        }
    }
}