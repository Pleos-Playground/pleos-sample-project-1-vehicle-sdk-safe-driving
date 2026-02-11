package ai.pleos.playground.handson.vehicle.data

import ai.pleos.playground.handson.vehicle.R
import android.content.res.Resources

sealed class WarningType(
    val score: Int,
    val toastTitleResId: Int,
    val toastDescResId: Int,
    val resultTitleResId: Int,
    val resultDescResId: Int
) {
    data object PeelingOut : WarningType(
        score = 5,
        toastTitleResId = R.string.toast_warning_title_peeling_out,
        toastDescResId = R.string.toast_warning_desc_peeling_out,
        resultTitleResId = R.string.result_warning_title_peeling_out,
        resultDescResId = R.string.result_warning_desc_peeling_out,
    )

    data object Incautious : WarningType(
        score = 5,
        toastTitleResId = R.string.toast_warning_title_incautious,
        toastDescResId = R.string.toast_warning_desc_incautious,
        resultTitleResId = R.string.result_warning_title_incautious,
        resultDescResId = R.string.result_warning_desc_incautious,
    )

    data object ScreechingHalt : WarningType(
        score = 5,
        toastTitleResId = R.string.toast_warning_title_screeching_halt,
        toastDescResId = R.string.toast_warning_desc_screeching_halt,
        resultTitleResId = R.string.result_warning_title_screeching_halt,
        resultDescResId = R.string.result_warning_desc_screeching_halt,
    )

    data object None : WarningType(
        score = 0,
        toastTitleResId = Resources.ID_NULL,
        toastDescResId = Resources.ID_NULL,
        resultTitleResId = Resources.ID_NULL,
        resultDescResId = Resources.ID_NULL,
    )
}