package ai.pleos.playground.handson.vehicle.ui.components

import ai.pleos.playground.handson.vehicle.data.TestStatus
import ai.pleos.playground.handson.vehicle.ui.preview.VehicleHandsonPreview
import ai.pleos.playground.handson.vehicle.ui.theme.VehicleTheme
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun Waiting(
    toastModifier: Modifier,
    @DrawableRes toastIconResId: Int,
    @StringRes toastResId: Int,
    footerContent: @Composable () -> Unit = {}
) {
    CommonToast(
        modifier = toastModifier,
        iconResId = toastIconResId,
        titleResId = toastResId
    )
    footerContent()
}

@VehicleHandsonPreview
@Composable
private fun WaitingPreview() {
    VehicleTheme {
        ConstraintLayout {
            val toast = createRef()
            val toastModifier = Modifier
                .constrainAs(toast) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.wrapContent
                }
            val status = TestStatus.Waiting
            Waiting(
                toastModifier = toastModifier,
                toastIconResId = status.toastIconResId,
                toastResId = status.toastResId
            )
        }
    }
}