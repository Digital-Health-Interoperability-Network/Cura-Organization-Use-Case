package com.nameksolutions.regchain.curaorganization.utils

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.nameksolutions.regchain.curaorganization.auth.ui.SignIn
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.network.Resource


//this file handles all API errors

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(failure: Resource.Failure, retry: (() -> Unit)? = null) {
    when {
        failure.isNetworkError == true -> requireView().snackbar(
            "Please check your internet connection",
            retry
        )
        failure.errorCode == 400 -> {
            if (this is SignIn) {
                requireView().snackbar("Incorrect email or password")
            } else {
//                requireView().snackbar("Will do logout")
                (this as BaseFragment<*, *, *>).logout()
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
            Log.d("EQUA", "handleApiError: $error")
        }
    }
}