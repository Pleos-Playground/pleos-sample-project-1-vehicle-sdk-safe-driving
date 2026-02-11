package ai.pleos.playground.handson.vehicle.ui.components

import ai.pleos.playground.handson.vehicle.R
import ai.pleos.playground.handson.vehicle.data.TestStatus
import ai.pleos.playground.handson.vehicle.data.WarningType
import ai.pleos.playground.handson.vehicle.ui.preview.VehicleHandsonPreview
import ai.pleos.playground.handson.vehicle.ui.theme.VehicleTheme
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun Warning(
    visibleState: MutableTransitionState<Boolean>,
    descriptionModifier: Modifier,
    toastModifier: Modifier,
    description: String,
    @DrawableRes toastIconResId: Int,
    @StringRes toastTitleResId: Int,
    @StringRes toastDescResId: Int,
    isUpdated: Boolean,
    footerContent: @Composable () -> Unit = {}
) {
    val (initAlpha, targetAlpha) = 0.4f to 0f
    val (enter, exit) = if (isUpdated) {
        fadeIn(
            initialAlpha = initAlpha,
            animationSpec = tween(500, 0, FastOutSlowInEasing)
        ) to fadeOut(
            targetAlpha = targetAlpha,
            animationSpec = tween(500, 0, LinearEasing)
        )
    } else {
        slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(500, 0, FastOutSlowInEasing)
        ) to slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(500, 0, LinearEasing)
        )
    }
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            initialAlpha = initAlpha,
            animationSpec = tween(500, 0, FastOutSlowInEasing)
        ),
        exit = fadeOut(
            targetAlpha = targetAlpha,
            animationSpec = tween(500, 0, LinearEasing)
        ),
        modifier = descriptionModifier,
    ) {
        Text(
            textAlign = TextAlign.Left,
            text = description,
            fontWeight = FontWeight.W700,
            fontSize = TextUnit(48f, TextUnitType.Sp),
            color = colorResource(id = R.color.system_informative_negative),
        )
    }
    AnimatedVisibility(
        visibleState = visibleState,
        enter = enter,
        exit = exit,
        modifier = toastModifier
    ) {
        WarningToast(
            modifier = toastModifier,
            iconResId = toastIconResId,
            titleResId = toastTitleResId,
            descResId = toastDescResId
        )
    }
    footerContent()
}

@VehicleHandsonPreview
@Composable
private fun WarningPreview() {
    VehicleTheme {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (desc, toast, footer) = createRefs()
            val descriptionModifier = Modifier
                .padding(top = 30.dp)
                .constrainAs(desc) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

            val toastModifier = Modifier
                .constrainAs(toast) {
                    bottom.linkTo(footer.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

            val status = TestStatus.Warning(
                warningType = WarningType.ScreechingHalt,
                time = System.currentTimeMillis(),
                isUpdated = true
            )
            Warning(
                visibleState = MutableTransitionState(true),
                descriptionModifier = descriptionModifier,
                toastModifier = toastModifier,
                description = stringResource(
                    R.string.desc_warning,
                    status.warningType.score
                ),
                toastIconResId = status.toastIconResId,
                toastTitleResId = status.warningType.toastTitleResId,
                toastDescResId = status.warningType.toastDescResId,
                isUpdated = status.isUpdated

            )
        }
    }
}
