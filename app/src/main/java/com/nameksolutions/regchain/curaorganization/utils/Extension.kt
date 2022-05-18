package com.nameksolutions.regchain.curaorganization.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import com.nameksolutions.regchain.curaorganization.R
import java.text.SimpleDateFormat
import java.util.*


//toast function
fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


//common function to handle progress bar visibility
fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}


//common function to handle all intent activity launches
fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

//common function to handle enabling the views (buttons)
fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    isClickable = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun Context.showProgressDialog(): Dialog {
    val progressDialog = Dialog(this)

    progressDialog.let {
        it.show()
        it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        it.setContentView(R.layout.loading_progress_dialog)
        it.setCancelable(false)
        it.setCanceledOnTouchOutside(false)
        return it
    }
}

//function to change milliseconds to date format
fun getDate(milliSeconds: Long?, dateFormat: String?): String? {
    // Create a DateFormatter object for displaying date in specified format.
    val formatter = SimpleDateFormat(dateFormat)

    // Create a calendar object that will convert the date and time value in milliseconds to date.
    val calendar: Calendar? = Calendar.getInstance()
    calendar?.setTimeInMillis(milliSeconds!!)
    return formatter.format(calendar?.getTime())
}
//
//fun Context.showProgressDialog() {
//    val dialog: LottieDialog = LottieDialog(this)
//        .setAnimation(R.raw.heart_rate_progress_bar)
//        .setAutoPlayAnimation(true)
//        .setDialogBackground(Color.TRANSPARENT)
//        .setDialogHeightPercentage(.2f)
//        .setAnimationRepeatCount(LottieDialog.INFINITE)
//        .setMessage("Loading...")
//        .setMessageColor(R.color.custom_color_day)
//
////    if (enabled) {
//        dialog.show()
////    } else {
////        dialog.cancel()
////    }


// TODO: 18/11/2021 find a way to cancel this progress bar after it has served its purpose

//    dialog.cancel()

//}
