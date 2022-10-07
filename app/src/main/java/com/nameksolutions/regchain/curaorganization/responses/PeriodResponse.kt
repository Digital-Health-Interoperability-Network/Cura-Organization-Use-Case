package com.nameksolutions.regchain.curaorganization.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PeriodResponse(
    val endDate: String,
    val startDate: String
) : Parcelable