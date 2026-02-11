package ai.pleos.playground.handson.vehicle.ui.components

import ai.pleos.playground.handson.vehicle.data.MainUIState
import ai.pleos.playground.handson.vehicle.data.TestStatus
import ai.pleos.playground.handson.vehicle.ui.dropShadow
import ai.pleos.playground.handson.vehicle.ui.preview.VehicleHandsonPreview
import ai.pleos.playground.handson.vehicle.ui.theme.VehicleTheme
import androidx.annotation.ColorRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


@Composable
fun BackgroundEffect(
    visibleState: MutableTransitionState<Boolean>,
    enterAnim: EnterTransition,
    exitAnim: ExitTransition,
    @ColorRes colorRes: Int,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visibleState = visibleState,
        enter = enterAnim,
        exit = exitAnim,
        modifier = modifier
    ) {
        Box(
            Modifier
                .dropShadow(
                    shape = RoundedCornerShape(100),
                    color = colorResource(colorRes),
                    offsetX = 0.dp,
                    offsetY = 200.dp,
                    blur = 200.dp,
                    spread = 0.dp,
                )
        )
    }
}

@VehicleHandsonPreview
@Composable
private fun BackgroundEffectPreview() {
    VehicleTheme {
        val uiState = MainUIState(
            100, TestStatus.Driving(showToast = false)
        )

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (background) = createRefs()
            BackgroundEffect(
                visibleState = MutableTransitionState(true),
                enterAnim = fadeIn(
                    initialAlpha = uiState.testStatus.backgroundInitAlpha,
                    animationSpec = TweenSpec(500, 0, FastOutSlowInEasing)
                ),
                exitAnim = fadeOut(
                    targetAlpha = 0.5f,
                    animationSpec = TweenSpec(500, 0, FastOutSlowInEasing)
                ),
                colorRes = uiState.testStatus.backgroundColorResId,
                modifier = Modifier
                    .constrainAs(background) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    }
            )
        }
    }
}