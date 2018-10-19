package me.tatocaster.nasaappchallenge.common.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import com.tapadoo.alerter.Alerter
import me.tatocaster.nasaappchallenge.R


fun showErrorAlert(activity: Activity, title: String, message: String) {
    Alerter.create(activity)
            .setTitle(title)
            .setText(message)
            .setBackgroundColorRes(R.color.errorBackground)
            .show()
}

fun showSuccessAlert(activity: Activity, message: String) {
    Alerter.create(activity)
            .setText(message)
            .setBackgroundColorRes(R.color.successBackground)
            .show()
}

fun showInfoAlert(activity: Activity, title: String, message: String) {
    Alerter.create(activity)
            .setTitle(title)
            .setText(message)
            .setBackgroundColorRes(R.color.infoBackground)
            .setIcon(R.drawable.ic_error_outline_white_24px)
            .show()
}

fun Context.dpToPx(dp: Int): Int {
    return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics))
}