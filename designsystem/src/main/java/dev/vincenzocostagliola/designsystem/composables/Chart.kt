package dev.vincenzocostagliola.designsystem.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vincenzocostagliola.designsystem.theme.Pink80
import dev.vincenzocostagliola.designsystem.theme.Purple80
import dev.vincenzocostagliola.designsystem.utils.formatPricesAsEuro
import dev.vincenzocostagliola.designsystem.utils.getSignificantPrices
import dev.vincenzocostagliola.designsystem.values.Dimens.Regular
import dev.vincenzocostagliola.designsystem.values.Dimens.XSmall
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import timber.log.Timber

@Composable
fun Chart(
    modifier: Modifier = Modifier,
    chartPricesPoints: List<Float>,
    chartDates: List<OffsetDateTime>,
    chartFormattedPrices: List<String>
) {
    val zipList: List<Pair<Float, Float>> = chartPricesPoints.zipWithNext()
    Timber.d("ChartComposable - prices: $zipList")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Regular)
    ) {
        Row(
            modifier = modifier
                .height(250.dp)
                .height(IntrinsicSize.Min)
        ) {

            ShowPrices(chartFormattedPrices, modifier)
            DrawCanvases(zipList, chartPricesPoints)
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = Regular, start = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            chartDates.forEach {
                Text(
                    text = it.format(dateFormatter),
                    style = extraSmallText
                )
            }
        }
    }
}

@Composable
fun ShowPrices(prices: List<String>, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(XSmall),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        prices.forEach {
            Text(
                text = it.toString(),
                style = extraSmallText
            )
        }
    }
}

@Composable
private fun RowScope.DrawCanvases(
    zipList: List<Pair<Float, Float>>,
    list: List<Float>
) {
    val max = list.max()
    val min = list.min()

    val lineColor =
        if (list.last() > list.first()) Purple80 else Pink80

    for (pair in zipList) {

        val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
        val toValuePercentage = getValuePercentageForRange(pair.second, max, min)

        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            onDraw = {
                val fromPoint =
                    Offset(x = 0f, y = size.height.times(1 - fromValuePercentage))
                val toPoint =
                    Offset(x = size.width, y = size.height.times(1 - toValuePercentage))

                drawLine(
                    color = lineColor,
                    start = fromPoint,
                    end = toPoint,
                    strokeWidth = 3f
                )
            })
    }
}

private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)

private fun getValuePercentageForRange(value: Float, max: Float, min: Float) =
    (value - min) / (max - min)


@Preview(heightDp = 300, widthDp = 300, backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun ShowChart() {
    val now = OffsetDateTime.of(2025, 5, 1, 1, 0, 0, 0, ZoneOffset.UTC)
    val datesList = listOf<OffsetDateTime>(
        now,
        now.plusDays(1),
        now.plusDays(2),
        now.plusDays(3),
        now.plusDays(4),
        now.plusDays(5),
        now.plusDays(6),
        now.plusDays(7)
    )

    val values = listOf(40f, 20f, 3f, 1f, 5f, 10f, 5f)

    Chart(
        modifier = Modifier,
        chartPricesPoints = values,
        chartDates = datesList,
        chartFormattedPrices = values.getSignificantPrices().formatPricesAsEuro()
    )
}