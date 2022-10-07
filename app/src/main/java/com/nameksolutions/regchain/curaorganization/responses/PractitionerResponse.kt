package com.nameksolutions.regchain.curaorganization.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class PractitionerResponse(
    val __v: Int,
    val _id: String = "",
    val _personnel: String = "",
    val address: @RawValue List<AddressResponse>? = null,
    val birthDate: String = "",
    val communication: @RawValue List<String> = listOf<String>(),
    val gender: String = "",
    val id: String = "",
    val identifier: @RawValue List<IdentifierResponse>? = null,
    val name: @RawValue NameResponse? = null,
    val qualification: @RawValue List<Any>? = null,
    val telecom: @RawValue List<TelecomResponse>,
    val practitionerRoles: List<PractitionerRole>
): Parcelable