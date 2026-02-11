package ai.pleos.playground.handson.vehicle.ui.components

import ai.pleos.playground.handson.vehicle.R
import ai.pleos.playground.handson.vehicle.ui.preview.VehicleHandsonPreview
import ai.pleos.playground.handson.vehicle.ui.theme.VehicleTheme
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
fun CommonToast(
    modifier: Modifier,
    @DrawableRes iconResId: Int,
    @StringRes titleResId: Int
) {
    Row(
        modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(colorResource(id = R.color.system_surface_basic))
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.system_divider_divider_territary_updated),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
        )

        Text(
            textAlign = TextAlign.Left,
            text = stringResource(id = titleResId),
            fontWeight = FontWeight.W700,
            fontSize = TextUnit(36f, TextUnitType.Sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.system_label_gray_secondary),
            modifier = Modifier
                .padding(start = 32.dp)
                .weight(1f),
        )
    }
}

@Composable
fun WarningToast(
    modifier: Modifier,
    @DrawableRes iconResId: Int,
    @StringRes titleResId: Int,
    @StringRes descResId: Int
) {
    Row(
        modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(colorResource(id = R.color.system_surface_basic))
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.system_informative_negative),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(vertical = 28.dp, horizontal = 44.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
        )

        Column(
            Modifier
                .padding(start = 32.dp)
                .weight(1f),
        ) {
            Text(
                textAlign = TextAlign.Left,
                text = stringResource(id = titleResId),
                fontWeight = FontWeight.W700,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(id = R.color.system_informative_negative),
                fontSize = TextUnit(36f, TextUnitType.Sp),
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                textAlign = TextAlign.Left,
                text = stringResource(id = descResId),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.W400,
                fontSize = TextUnit(32f, TextUnitType.Sp),
                color = colorResource(id = R.color.system_label_gray_primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
            )
        }
    }
}

@VehicleHandsonPreview
@Composable
private fun CommonToastPreview_Driving() {
    VehicleTheme {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val toast = createRef()
            val toastModifier = Modifier
                .constrainAs(toast) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.wrapContent
                }
            CommonToast(
                modifier = toastModifier,
                iconResId = R.drawable.ic_check_green,
                titleResId = R.string.toast_driving
            )
        }
    }
}

@VehicleHandsonPreview
@Composable
private fun CommonToastPreview_Waiting() {
    VehicleTheme {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val toast = createRef()
            val toastModifier = Modifier
                .constrainAs(toast) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.wrapContent
                }
            CommonToast(
                modifier = toastModifier,
                iconResId = R.drawable.ic_telltale,
                titleResId = R.string.toast_waiting,
            )
        }
    }
}

@VehicleHandsonPreview
@Composable
private fun WarningToastPreview() {
    VehicleTheme {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val toast = createRef()
            val toastModifier = Modifier
                .constrainAs(toast) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.wrapContent
                }
            WarningToast(
                modifier = toastModifier,
                iconResId = R.drawable.ic_annotation,
                titleResId = R.string.toast_warning_title_peeling_out,
                descResId = R.string.toast_warning_desc_peeling_out
            )
        }
    }
}