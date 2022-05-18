package com.nameksolutions.regchain.curaorganization.utils

import android.app.TimePickerDialog
import android.content.Context
import android.app.TimePickerDialog.OnTimeSetListener
import com.nameksolutions.regchain.curaorganization.R
import java.util.*

class TimePickerHelper(context: Context, is24HourView: Boolean, isSpinnerType: Boolean = false) {

    private var dialog: TimePickerDialog
    private var callback: Callback? = null

    private val listener = OnTimeSetListener { timePicker, hourOfDay, minute ->
        callback?.onTimeSelected(hourOfDay, minute)
    }

    init {
        val style = if (isSpinnerType)
            R.style.SpinnerTimePickerDialog else 0

        val cal = Calendar.getInstance()

        dialog = TimePickerDialog(context, style, listener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), is24HourView)
    }

    fun showDialog(hourOfDay: Int, minute: Int, callback: Callback?){
        this.callback = callback
        dialog.updateTime(hourOfDay, minute)
        dialog.show()
    }

    interface Callback {
        fun onTimeSelected(hourOfDay: Int, minute: Int)
    }
}