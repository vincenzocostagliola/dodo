package dev.vincenzocostagliola.designsystem.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.vincenzocostagliola.designsystem.theme.Dark
import dev.vincenzocostagliola.designsystem.R
import dev.vincenzocostagliola.designsystem.values.Dimens


val geomanistRegular = FontFamily(Font(R.font.geomanist_regular, FontWeight.Normal))
val geomanistMedium = FontFamily(Font(R.font.geomanist_medium, FontWeight.Normal))
val geomanistBold = FontFamily(Font(R.font.geomanist_bold, FontWeight.Normal))

val regularTextStyle = TextStyle(
    color = Dark,
    fontFamily = geomanistRegular,
//    lineHeight = 21.sp
)

val mediumTextStyle = TextStyle(
    color = Dark,
    fontFamily = geomanistRegular,
    fontWeight = FontWeight.SemiBold,
//    lineHeight = 20.4.sp
)

val boldTextStyle = TextStyle(
    color = Dark,
    fontFamily = geomanistBold,
)

val extraSmallText = regularTextStyle.copy(fontSize = 9.sp, lineHeight = 11.7.sp)
val smallText = regularTextStyle.copy(fontSize = 12.sp, lineHeight = 15.6.sp)
val normalText = regularTextStyle.copy(fontSize = 15.sp, lineHeight = 21.sp)
val mediumText = regularTextStyle.copy(fontSize = 17.sp, lineHeight = 20.4.sp)
val largeText = regularTextStyle.copy(fontSize = 21.sp, lineHeight = 25.2.sp)

val extraSmallTextSemiBold = mediumTextStyle.copy(fontSize = 9.sp, lineHeight = 11.7.sp)
val smallTextSemiBold = mediumTextStyle.copy(fontSize = 12.sp, lineHeight = 15.6.sp)
val normalTextSemiBold = mediumTextStyle.copy(fontSize = 15.sp, lineHeight = 19.5.sp)
val mediumTextSemiBold = mediumTextStyle.copy(fontSize = 17.sp, lineHeight = 20.4.sp)
val largeTextSemiBold = mediumTextStyle.copy(fontSize = 21.sp, lineHeight = 25.2.sp)

val heading1 = boldTextStyle.copy(fontSize = 80.sp)
val heading2 = boldTextStyle.copy(fontSize = 60.sp)
val heading3 = boldTextStyle.copy(fontSize = 27.sp)
val heading4 = boldTextStyle.copy(fontSize = 21.sp)

@Preview(showBackground = true)
@Composable
fun TextsPreview() {
    Column(modifier = Modifier.padding(Dimens.Regular)) {
        Text(
            text = "extraSmallText",
            style = extraSmallText
        )
        Text(
            text = "extraSmallTextSemiBold",
            style = extraSmallTextSemiBold
        )
        Text(
            text = "smallText",
            style = smallText
        )
        Text(
            text = "smallTextSemiBold",
            style = smallTextSemiBold
        )
        Text(
            text = "normalText",
            style = normalText
        )
        Text(
            text = "normalTextSemiBold",
            style = normalTextSemiBold
        )
        Text(
            text = "mediumText",
            style = mediumText
        )
        Text(
            text = "mediumTextSemiBold",
            style = mediumTextSemiBold
        )
        Text(
            text = "largeText",
            style = largeText
        )
        Text(
            text = "largeTextSemiBold",
            style = largeTextSemiBold
        )
        Text(
            text = "heading4",
            style = heading4
        )
    }
}
