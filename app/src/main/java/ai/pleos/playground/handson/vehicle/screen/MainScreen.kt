package ai.pleos.playground.handson.vehicle.screen

import ai.pleos.playground.handson.vehicle.R
import ai.pleos.playground.handson.vehicle.data.MainUIState
import ai.pleos.playground.handson.vehicle.data.TestStatus
import ai.pleos.playground.handson.vehicle.ui.components.BackgroundEffect
import ai.pleos.playground.handson.vehicle.ui.components.Footer
import ai.pleos.playground.handson.vehicle.ui.components.Others
import ai.pleos.playground.handson.vehicle.ui.components.Result
import ai.pleos.playground.handson.vehicle.ui.components.Score
import ai.pleos.playground.handson.vehicle.ui.components.VehicleToolbar
import ai.pleos.playground.handson.vehicle.ui.components.Waiting
import ai.pleos.playground.handson.vehicle.ui.components.Warning
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.delay

@Composable
fun MainScreen(uiState: MainUIState, showDriving: () -> Unit, showResult: () -> Unit) {
    val backgroundVisibleState =
        remember { MutableTransitionState(false) }.apply { targetState = true }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (toolbar, title, score, desc, result, toast, footer, background) = createRefs()

        VehicleToolbar(Modifier.constrainAs(toolbar) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        })
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = uiState.testStatus.titleResId),
            fontWeight = FontWeight.W700,
            fontSize = TextUnit(36f, TextUnitType.Sp),
            color = colorResource(id = R.color.system_label_gray_teritiary),
            modifier = Modifier
                .padding(top = 80.dp)
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(toolbar.bottom)
                },
        )

        Score(
            score = uiState.score,
            modifier = Modifier
                .padding(top = 120.dp)
                .wrapContentSize()
                .constrainAs(score) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(title.bottom)
                }
        )

        BackgroundEffect(
            visibleState = backgroundVisibleState,
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
                    top.linkTo(score.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
        )

        val descriptionModifier = Modifier
            .padding(top = 30.dp)
            .constrainAs(desc) {
                top.linkTo(score.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
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

        when (val drivingStatus = uiState.testStatus) {
            is TestStatus.Waiting -> {
                Waiting(
                    toastModifier = toastModifier,
                    toastIconResId = drivingStatus.toastIconResId,
                    toastResId = drivingStatus.toastResId,
                ) {
                    Footer(
                        modifier = footerModifier,
                        footerResId = drivingStatus.footerResId
                    )
                }
            }

            is TestStatus.Driving -> {
                val toastVisibleState =
                    remember { MutableTransitionState(false) }.apply {
                        targetState = drivingStatus.showToast
                    }
                Others(
                    visibleState = toastVisibleState,
                    descriptionModifier = descriptionModifier,
                    toastModifier = toastModifier,
                    descResId = drivingStatus.descResId,
                    toastIconResId = drivingStatus.toastIconResId,
                    toastResId = drivingStatus.toastResId,
                ) {
                    Footer(
                        modifier = footerModifier,
                        footerResId = drivingStatus.footerResId
                    )
                }
                LaunchedEffect(Unit) {
                    while (true) {
                        delay(500)
                        backgroundVisibleState.targetState = !backgroundVisibleState.targetState
                    }
                }

                if (drivingStatus.showToast) {
                    LaunchedEffect(Unit) {
                        delay(2500)
                        toastVisibleState.targetState = false
                    }
                }
            }

            is TestStatus.Warning -> {
                val toastVisibleState =
                    remember(drivingStatus) { MutableTransitionState(false) }.apply { targetState = true }
                Warning(
                    visibleState = toastVisibleState,
                    descriptionModifier = descriptionModifier,
                    toastModifier = toastModifier,
                    description = stringResource(
                        id = drivingStatus.descResId,
                        drivingStatus.warningType.score
                    ),
                    toastIconResId = drivingStatus.toastIconResId,
                    toastTitleResId = drivingStatus.warningType.toastTitleResId,
                    toastDescResId = drivingStatus.warningType.toastDescResId,
                    isUpdated = drivingStatus.isUpdated
                ) {
                    Footer(
                        modifier = footerModifier,
                        footerResId = drivingStatus.footerResId
                    )
                }

                LaunchedEffect(drivingStatus) {
                    while (true) {
                        delay(500)
                        backgroundVisibleState.targetState = !backgroundVisibleState.targetState
                    }
                }

                LaunchedEffect(drivingStatus) {
                    delay(2500)
                    toastVisibleState.targetState = false
                    delay(700)
                    showDriving()
                }
            }

            is TestStatus.End -> {
                val toastVisibleState =
                    remember { MutableTransitionState(false) }.apply { targetState = true }
                Others(
                    visibleState = toastVisibleState,
                    descriptionModifier = descriptionModifier,
                    toastModifier = toastModifier,
                    descResId = drivingStatus.descResId,
                    toastIconResId = drivingStatus.toastIconResId,
                    toastResId = drivingStatus.toastResId,
                ) {
                    Footer(
                        modifier = footerModifier,
                        footerResId = drivingStatus.footerResId
                    )
                }

                LaunchedEffect(Unit) {
                    delay(timeMillis = 1500L)
                    toastVisibleState.targetState = false
                    delay(timeMillis = 700L)
                    showResult()
                }
            }

            is TestStatus.Result -> {
                val resultModifier = Modifier
                    .constrainAs(result) {
                        top.linkTo(desc.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
                Result(
                    descriptionModifier = descriptionModifier,
                    resultModifier = resultModifier,
                    descResId = drivingStatus.descResId,
                    classResId = drivingStatus.getDriverClassResId(score = uiState.score),
                    warningMap = drivingStatus.warningMap,
                )
            }
        }
    }
}