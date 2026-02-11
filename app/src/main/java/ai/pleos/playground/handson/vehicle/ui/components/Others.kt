package ai.pleos.playground.handson.vehicle.ui.components

import ai.pleos.playground.handson.vehicle.R
import ai.pleos.playground.handson.vehicle.data.TestStatus
import ai.pleos.playground.handson.vehicle.ui.preview.VehicleHandsonPreview
import ai.pleos.playground.handson.vehicle.ui.theme.VehicleTheme
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun Others(
    visibleState: MutableTransitionState<Boolean>,
    descriptionModifier: Modifier,
    toastModifier: Modifier,
    @StringRes descResId: Int,
    @DrawableRes toastIconResId: Int,
    @StringRes toastResId: Int,
    footerContent: @Composable () -> Unit = {}
) {
    Text(
        textAlign = TextAlign.Left,
        text = stringResource(id = descResId),
        fontWeight = FontWeight.W700,
        fontSize = TextUnit(48f, TextUnitType.Sp),
        color = colorResource(id = R.color.system_label_gray_teritiary),
        modifier = descriptionModifier,
    )

    AnimatedVisibility(
        visibleState = visibleState,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = TweenSpec(500, 0, FastOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = TweenSpec(500, 0, LinearEasing)
        ),
        modifier = toastModifier
    ) {
        CommonToast(
            modifier = Modifier,
            iconResId = toastIconResId,
            titleResId = toastResId
        )
    }
    footerContent()
}

@VehicleHandsonPreview
@Composable
private fun OthersPreview_Driving() {
    VehicleTheme {
        ConstraintLayout {
            val (desc, toast, footer) = createRefs()
            val descriptionModifier = Modifier
                .padding(top = 30.dp)
                .constrainAs(desc) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.wrapContent
                }
            val toastModifier = Modifier
                .constrainAs(toast) {
                    bottom.linkTo(footer.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            val footerModifier = Modifier
                .padding(top = 30.dp)
                .constrainAs(footer) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            val drivingStatus = TestStatus.Driving(showToast = false)
            Others(
                visibleState = MutableTransitionState(false),
                descriptionModifier = descriptionModifier,
                toastModifier = toastModifier,
                descResId = drivingStatus.descResId,
                toastIconResId = drivingStatus.toastIconResId,
                toastResId = drivingStatus.toastResId,
            ) {
                Box(modifier = footerModifier) {}
            }
        }
    }
}

@VehicleHandsonPreview
@Composable
private fun OthersPreview_End() {
    VehicleTheme {
        ConstraintLayout {
            val (desc, toast, footer) = createRefs()
            val descriptionModifier = Modifier
                .padding(top = 30.dp)
                .constrainAs(desc) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.wrapContent
                }
            val toastModifier = Modifier
                .constrainAs(toast) {
                    bottom.linkTo(footer.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            val footerModifier = Modifier
                .padding(top = 30.dp)
                .constrainAs(footer) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            val drivingStatus = TestStatus.End
            Others(
                visibleState = MutableTransitionState(false),
                descriptionModifier = descriptionModifier,
                toastModifier = toastModifier,
                descResId = drivingStatus.descResId,
                toastIconResId = drivingStatus.toastIconResId,
                toastResId = drivingStatus.toastResId,
            ) {
                Box(modifier = footerModifier) {}
            }
        }
    }
}
