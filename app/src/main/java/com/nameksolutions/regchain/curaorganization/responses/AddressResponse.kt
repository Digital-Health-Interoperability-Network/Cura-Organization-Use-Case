package com.nameksolutions.regchain.curaorganization.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class AddressResponse(
    val _id: String = "",
    val city: String = "",
    val district: String = "",
    val line: @RawValue List<String> = listOf<String>(),
    val period: @RawValue PeriodResponse? = null,
    val postalCode: String = "",
    val state: String = "",
    val text: String = "",
    val type: String = "",
    val use: String = ""
) : Parcelable