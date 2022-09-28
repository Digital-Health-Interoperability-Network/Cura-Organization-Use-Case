package com.nameksolutions.regchain.curaorganization.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PractitionerResponse(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val address: List<AddressResponse>,
    val birthDate: String,
    val communication: List<String>,
    val gender: String,
    val id: String,
    val identifier: List<IdentifierResponse>,
    val name: NameResponse,
    val qualification: List<Any>,
    val telecom: List<TelecomResponse>
): Parcelable