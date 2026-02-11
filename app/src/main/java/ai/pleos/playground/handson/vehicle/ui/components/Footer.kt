package ai.pleos.playground.handson.vehicle.ui.components

import ai.pleos.playground.handson.vehicle.R
import ai.pleos.playground.handson.vehicle.ui.preview.VehicleHandsonPreview
import ai.pleos.playground.handson.vehicle.ui.theme.VehicleTheme
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun Footer(modifier: Modifier, @StringRes footerResId: Int) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.system_surface_low)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = colorResource(id = R.color.system_divider_divider_secondary_updated),
            modifier = Modifier.padding(bottom = 60.dp)
        )
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = footerResId),
            fontWeight = FontWeight.W400,
            fontSize = TextUnit(32f, TextUnitType.Sp),
            lineHeight = TextUnit(40f, TextUnitType.Sp),
            color = colorResource(id = R.color.system_label_gray_primary),
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.footer_common),
            fontWeight = FontWeight.W700,
            fontSize = TextUnit(32f, TextUnitType.Sp),
            color = colorResource(id = R.color.system_label_gray_secondary),
            modifier = Modifier
                .padding(top = 16.dp, bottom = 80.dp)
                .align(Alignment.CenterHorizontally),
        )
    }
}

@VehicleHandsonPreview
@Composable
private fun FooterPreview_Common() {
    VehicleTheme {
        ConstraintLayout {
            val footer = createRef()
            Footer(
                modifier = Modifier.constrainAs(footer) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                footerResId = R.string.footer_common
            )
        }
    }
}

@VehicleHandsonPreview
@Composable
private fun FooterPreview_Default() {
    VehicleTheme {
        ConstraintLayout {
            val footer = createRef()
            Footer(
                modifier = Modifier.constrainAs(footer) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                footerResId = R.string.footer_default
            )
        }
    }
}

@VehicleHandsonPreview
@Composable
private fun FooterPreview_Waiting() {
    VehicleTheme {
        ConstraintLayout {
            val footer = createRef()
            Footer(
                modifier = Modifier.constrainAs(footer) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                footerResId = R.string.footer_waiting
            )
        }
    }
}