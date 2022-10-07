package com.nameksolutions.regchain.curaorganization.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IdentifierResponse(
    val _id: String = "",
    val assigner: String = "",
    val partOf: String = "",
    val period: PeriodResponse? = null,
    val system: String = "",
    val type: String = "",
    val use: String = "",
    val value: String = ""
) : Parcelable