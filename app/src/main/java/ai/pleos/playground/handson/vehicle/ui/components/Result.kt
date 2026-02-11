package ai.pleos.playground.handson.vehicle.ui.components

import ai.pleos.playground.handson.vehicle.R
import ai.pleos.playground.handson.vehicle.data.TestStatus
import ai.pleos.playground.handson.vehicle.data.WarningType
import ai.pleos.playground.handson.vehicle.ui.preview.VehicleHandsonPreview
import ai.pleos.playground.handson.vehicle.ui.theme.VehicleTheme
import ai.pleos.playground.handson.vehicle.vm.Count
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun Result(
    descriptionModifier: Modifier,
    resultModifier: Modifier,
    @StringRes descResId: Int,
    @StringRes classResId: Int,
    warningMap: Map<WarningType, Count>
) {
    Text(
        textAlign = TextAlign.Left,
        text = stringResource(id = descResId),
        fontWeight = FontWeight.W700,
        fontSize = TextUnit(48f, TextUnitType.Sp),
        color = colorResource(id = R.color.system_label_gray_teritiary),
        modifier = descriptionModifier,
    )

    Column(
        modifier = resultModifier.padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = classResId),
            fontWeight = FontWeight.W800,
            fontSize = TextUnit(56f, TextUnitType.Sp),
            color = colorResource(id = R.color.system_informative_positive),
        )
        Spacer(modifier = Modifier.weight(1f))

        if (warningMap.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(40.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(colorResource(id = R.color.system_surface_basic))
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.system_divider_divider_territary_updated),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(40.dp),
            ) {
                Text(
                    textAlign = TextAlign.Left,
                    text = stringResource(id = R.string.result_title),
                    fontWeight = FontWeight.W700,
                    fontSize = TextUnit(36f, TextUnitType.Sp),
                    color = colorResource(id = R.color.system_label_gray_secondary),
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorResource(id = R.color.divider_divier_1),
                    modifier = Modifier.padding(vertical = 32.dp)
                )
                warningMap.forEach { (type, count) ->
                    ResultItem(type, count)
                }
            }
        }
    }
}

@Composable
private fun ResultItem(warningType: WarningType, count: Int) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            textAlign = TextAlign.Left,
            text = stringResource(id = warningType.resultTitleResId),
            fontWeight = FontWeight.W700,
            maxLines = 1,
            fontSize = TextUnit(28f, TextUnitType.Sp),
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.system_label_gray_secondary),
        )

        Text(
            textAlign = TextAlign.Left,
            text = stringResource(id = warningType.resultDescResId),
            fontWeight = FontWeight.W400,
            fontSize = TextUnit(24f, TextUnitType.Sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.system_label_gray_teritiary),
            modifier = Modifier.padding(start = 8.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            textAlign = TextAlign.Left,
            text = stringResource(id = R.string.result_warning_count, count),
            fontSize = TextUnit(28f, TextUnitType.Sp),
            fontWeight = FontWeight.W700,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.system_label_gray_secondary),
        )

        Text(
            textAlign = TextAlign.Left,
            text = "/",
            fontSize = TextUnit(28f, TextUnitType.Sp),
            fontWeight = FontWeight.W400,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.system_label_gray_quaternary),
            modifier = Modifier
                .padding(horizontal = 12.dp),
        )

        var score = count * warningType.score
        if (score > 100) {
            score = 100
        }

        Text(
            textAlign = TextAlign.Left,
            text = stringResource(id = R.string.result_warning_score, -score),
            fontSize = TextUnit(28f, TextUnitType.Sp),
            fontWeight = FontWeight.W700,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.system_informative_negative),
        )
    }
}

@VehicleHandsonPreview
@Composable
private fun ResultPreview() {
    VehicleTheme {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (result, desc) = createRefs()
            val descriptionModifier = Modifier
                .padding(top = 30.dp)
                .constrainAs(desc) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

            val resultModifier = Modifier
                .constrainAs(result) {
                    top.linkTo(desc.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }

            val drivingStatus = TestStatus.Result(
                warningMap = mapOf(
                    WarningType.Incautious to 3
                )
            )
            Result(
                descriptionModifier = descriptionModifier,
                resultModifier = resultModifier,
                descResId = drivingStatus.descResId,
                classResId = drivingStatus.getDriverClassResId(score = 85),
                warningMap = drivingStatus.warningMap,
            )
        }
    }
}
