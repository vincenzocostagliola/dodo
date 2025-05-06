package dev.vincenzocostagliola.designsystem.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vincenzocostagliola.data.R
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.data.error.DialogAction.Leave
import dev.vincenzocostagliola.data.error.DialogAction.Retry
import dev.vincenzocostagliola.data.error.ErrorResources
import dev.vincenzocostagliola.designsystem.theme.Pink80
import dev.vincenzocostagliola.designsystem.theme.Purple40
import dev.vincenzocostagliola.designsystem.theme.buttonBorder
import dev.vincenzocostagliola.designsystem.values.Dimens.Small
import dev.vincenzocostagliola.designsystem.values.Dimens.XRegular
import dev.vincenzocostagliola.designsystem.values.Dimens.buttonCornerShape
import dev.vincenzocostagliola.designsystem.values.Dimens.defaultSpacer
import dev.vincenzocostagliola.designsystem.values.Dimens.iconDimensSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(errorResources: ErrorResources, performAction: (DialogAction) -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        BasicAlertDialog(
            onDismissRequest = {
                openDialog.value = false
                performAction(Leave)
            }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(XRegular)) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Pink80, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                imageVector = Icons.Filled.WarningAmber,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.White),
                                modifier = Modifier.size(iconDimensSmall)
                            )
                        }
                    }
                    Spacer(Modifier.size(defaultSpacer))
                    Text(
                        text =
                            stringResource(errorResources.errorTextResource),
                    )
                    Spacer(modifier = Modifier.height(defaultSpacer))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            Small,
                            alignment = Alignment.End
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        //Secondary Action
                        errorResources.secondaryAction?.let { secondaryAction ->
                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(buttonCornerShape))
                                    .clickable(role = Role.Button) {
                                        openDialog.value = false
                                        performAction(secondaryAction)
                                    }
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                BasicText(
                                    text = stringResource(secondaryAction.stringIdResource),
                                    style = normalTextSemiBold
                                )
                            }
                        }
                        //Main Action
                        Box(
                            Modifier
                                .clip(RoundedCornerShape(buttonCornerShape))
                                .clickable(role = Role.Button) {
                                    openDialog.value = false
                                    performAction(errorResources.mainAction)
                                }
                                .background(Purple40)
                                .border(1.dp, buttonBorder, RoundedCornerShape(buttonCornerShape))
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicText(
                                text = stringResource(errorResources.mainAction.stringIdResource),
                                style = normalTextSemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ErrorDialogPreview() {
    val errorResources: ErrorResources = ErrorResources(
        errorTextResource = R.string.unknown_error,
        mainAction = Retry,
        secondaryAction = Leave
    )

    ErrorDialog(errorResources, {})
}