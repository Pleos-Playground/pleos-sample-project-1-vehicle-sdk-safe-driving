package ai.pleos.playground.handson.vehicle.data

import ai.pleos.playground.handson.vehicle.R
import ai.pleos.playground.handson.vehicle.vm.Count
import android.content.res.Resources

sealed class TestStatus(
    val titleResId: Int,
    val descResId: Int,
    val toastIconResId: Int,
    val toastResId: Int,
    val footerResId: Int,
    val backgroundColorResId: Int,
    val backgroundInitAlpha: Float,
) {
    data object Waiting :
        TestStatus(
            titleResId = R.string.title_waiting,
            descResId = Resources.ID_NULL,
            toastIconResId = R.drawable.ic_telltale,
            toastResId = R.string.toast_waiting,
            footerResId = R.string.footer_waiting,
            backgroundColorResId = R.color.system_informative_positive_blur,
            backgroundInitAlpha = 1.0f,
        )

    data class Driving(val showToast: Boolean) :
        TestStatus(
            titleResId = R.string.title_driving,
            descResId = R.string.desc_driving,
            toastIconResId = R.drawable.ic_check_green,
            toastResId = R.string.toast_driving,
            footerResId = R.string.footer_default,
            backgroundColorResId = R.color.system_informative_active_blur,
            backgroundInitAlpha = 0.5f,
        )

    data class Warning(val warningType: WarningType, val time: Long, val isUpdated: Boolean) :
        TestStatus(
            titleResId = R.string.title_driving,
            descResId = R.string.desc_warning,
            toastIconResId = R.drawable.ic_annotation,
            toastResId = Resources.ID_NULL,
            footerResId = R.string.footer_default,
            backgroundColorResId = R.color.system_informative_negative_blur,
            backgroundInitAlpha = 0.5f,
        )

    data object End :
        TestStatus(
            titleResId = R.string.title_end,
            descResId = R.string.desc_end,
            toastIconResId = R.drawable.ic_check_blue,
            toastResId = R.string.toast_end,
            footerResId = R.string.footer_default,
            backgroundColorResId = R.color.system_informative_positive_blur,
            backgroundInitAlpha = 1f,
        )

    data class Result(val warningMap: Map<WarningType, Count>) :
        TestStatus(
            titleResId = R.string.title_end,
            descResId = R.string.desc_end,
            toastIconResId = Resources.ID_NULL,
            toastResId = Resources.ID_NULL,
            footerResId = Resources.ID_NULL,
            backgroundColorResId = R.color.system_informative_positive_blur,
            backgroundInitAlpha = 1f,
        ) {
        fun getDriverClassResId(score: Int): Int {
            return when {
                score == 100 -> R.string.class_100
                score >= 70 -> R.string.class_70
                score >= 50 -> R.string.class_50
                else -> R.string.class_others
            }
        }
    }
}